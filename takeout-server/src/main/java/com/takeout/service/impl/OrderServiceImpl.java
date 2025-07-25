package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.PageHelper;
import com.takeout.constant.MessageConst;
import com.takeout.constant.OrderConst;
import com.takeout.constant.PayStatusConst;
import com.takeout.context.LoginContext;
import com.takeout.dto.OrderConfirmDTO;
import com.takeout.dto.OrderDTO;
import com.takeout.dto.OrderCancelDTO;
import com.takeout.dto.OrdersRejectionDTO;
import com.takeout.dto.page.OrdersPageQueryDTO;
import com.takeout.entity.AddressBook;
import com.takeout.entity.Order;
import com.takeout.entity.OrderDetail;
import com.takeout.entity.ShopCart;
import com.takeout.exception.NotContainShopsException;
import com.takeout.exception.OrderBusinessException;
import com.takeout.mapper.AddressBookMapper;
import com.takeout.mapper.OrderDetailMapper;
import com.takeout.mapper.OrderMapper;
import com.takeout.mapper.ShopCartMapper;
import com.takeout.service.OrderService;
import com.takeout.util.PageResult;
import com.takeout.vo.OrderStatisticsVO;
import com.takeout.vo.OrderSubmitVO;
import com.takeout.vo.OrderVO;
import com.takeout.websocket.WebSocketServer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    AddressBookMapper addressBookMapper;
    @Autowired
    ShopCartMapper shopCartMapper;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    WebSocketServer webSocketServer;

    @Override
    public OrderSubmitVO submitOrder(OrderDTO orderDTO) {
        AddressBook addressBook = addressBookMapper.getById(orderDTO.getAddressBookId());
        // addressBook是空时抛出异常
        addressBookIsNull(addressBook);

        Long userId = LoginContext.getCurrentId();

        ShopCart shopCart = new ShopCart();
        shopCart.setUserId(userId);

        List<ShopCart> shopCartList = shopCartMapper.list(shopCart);
        // 购物车不包含任何商品抛出异常
        shopCartNotContainsShops(shopCartList);

        // 添加新的订单
        Order order = createOrder(orderDTO, addressBook, userId);
        orderMapper.addOrder(order);


        // 创建订单明细数据, 购物车中每一件商品都创建一个订单
        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (ShopCart cart : shopCartList) {
            OrderDetail orderDetail = new OrderDetail();
            // 复制购物车属性到订单明细
            BeanUtils.copyProperties(cart, orderDetail);
            // 设置订单ID关联
            orderDetail.setOrderId(order.getId());

            orderDetailList.add(orderDetail);
        }

        // 批量插入订单明细到数据库
        orderDetailMapper.insertBatch(orderDetailList);
        // 清理用户购物车数据
        cleanShopCart(userId);
        // 封装并返回订单提交结果
        OrderSubmitVO orderVO = OrderSubmitVO.builder()
                                             .id(order.getId())                    // 订单ID
                                             .orderNumber(order.getNumber())       // 订单号
                                             .orderAmount(order.getAmount())       // 订单金额
                                             .orderTime(order.getOrderTime())      // 下单时间
                                             .build();
        return orderVO;
    }

    @Override
    public void reminder(Long id) {
        // 查询订单是否存在
        Order order = orderMapper.queryById(id);
        if (order == null)
            throw new RuntimeException(MessageConst.ORDER_NOT_FOUND);

        // 基于 WebSocket 实现催单
        Map<String, Object> map = new HashMap<>();
        map.put("type", 2); // 2 代表用户催单
        map.put("orderId", id);
        map.put("content", "订单号：" + order.getNumber());
        webSocketServer.sendToAllClient(JSONUtil.toJsonStr(map));
    }

    @Override
    public PageResult<OrderVO> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        int pageNum = ordersPageQueryDTO.getPage();
        int pageSize = ordersPageQueryDTO.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        List<Order> orders = orderMapper.pageQuery(ordersPageQueryDTO);
        return new PageResult<>(orders.size(), convertToOrderVO(orders));
    }

    @Override
    public OrderStatisticsVO statistics() {
        // 根据状态，分别查询出接待单，待派送、派送中的订单数量
        Integer toBeConfirmed = orderMapper.countStatus(OrderConst.PENDING_ACCEPT);
        Integer confirmed = orderMapper.countStatus(OrderConst.ACCEPTED);
        Integer deliveryInProgress = orderMapper.countStatus(OrderConst.DELIVERING);

        // 将查询出的数据封装
        OrderStatisticsVO orderStatisticsVO = new OrderStatisticsVO();
        orderStatisticsVO.setToBeConfirmed(toBeConfirmed);
        orderStatisticsVO.setConfirmed(confirmed);
        orderStatisticsVO.setDeliveryInProgress(deliveryInProgress);

        return orderStatisticsVO;
    }

    @Override
    public OrderVO details(Long id) {
        Order order = orderMapper.queryById(id);

        OrderVO orderVO = BeanUtil.copyProperties(order, OrderVO.class);
        List<OrderDetail> orderDetails = orderDetailMapper.queryByOrderId(id);
        orderVO.setOrderDetailList(orderDetails);

        return orderVO;
    }

    @Override
    public void confirm(OrderConfirmDTO orderConfirmDTO) {
        Order order = new Order();
        order.setId(orderConfirmDTO.getId());
        order.setStatus(OrderConst.COMPLETED);

        orderMapper.update(order);
    }

    @Override
    public void rejection(OrdersRejectionDTO ordersRejectionDTO) {
        Long orderId = ordersRejectionDTO.getId();
        Order order = orderMapper.queryById(orderId);
        // 不可拒绝订单时抛出异常, 只有处于状态2的订单能够拒绝
        checkCanRejectOrder(order);
        // 如果用户已经支付, 需要进行退款
        refundIfPaid(order);
        // 拒单需要退款，根据订单id更新订单状态，拒单原因，取消时间
        order.setId(orderId);
        order.setStatus(OrderConst.CANCELLED);
        order.setRejectionReason(ordersRejectionDTO.getRejectionReason());
        order.setCancelTime(LocalDateTime.now());

        orderMapper.update(order);
    }

    @Override
    public void cancel(OrderCancelDTO orderCancelDTO) {
        Long orderId = orderCancelDTO.getId();
        Order order = orderMapper.queryById(orderId);

        refundIfPaid(order);

        // 管理端取消订单需要退款，根据订单id更新订单状态、取消原因、取消时间
        order.setId(orderId);
        order.setStatus(OrderConst.CANCELLED);
        order.setCancelReason(orderCancelDTO.getCancelReason());
        order.setCancelTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    @Override
    public void delivery(Long id) {
        Order order = orderMapper.queryById(id);

        checkCanDeliveryOrder(order);

        order.setStatus(OrderConst.DELIVERING);
        orderMapper.update(order);
    }

    @Override
    public void complete(Long id) {
        Order order = orderMapper.queryById(id);

        checkCanCompleteOrder(order);

        order.setStatus(OrderConst.COMPLETED);
        order.setDeliveryTime(LocalDateTime.now());
        orderMapper.update(order);
    }

    private void checkCanCompleteOrder(Order order) {
        if (order == null || order.getStatus() == OrderConst.DELIVERING)
            throw new OrderBusinessException(MessageConst.ORDER_STATUS_ERROR);
    }

    private void checkCanDeliveryOrder(Order order) {
        if (order == null || order.getStatus() != OrderConst.ACCEPTED)
            throw new OrderBusinessException(MessageConst.ORDER_STATUS_ERROR);
    }

    private void refundIfPaid(Order order) {
        if (!order.getPayStatus().equals(PayStatusConst.PAID))
            return;

        // 退款逻辑
    }

    private void checkCanRejectOrder(Order order) {
        if (order == null || OrderConst.PENDING_ACCEPT != order.getStatus())
            throw new OrderBusinessException(MessageConst.ORDER_STATUS_ERROR);
    }

    private String getOrderDishesStr(Long orderId) {
        List<OrderDetail> orderDetails = orderDetailMapper.queryByOrderId(orderId);
        List<String> orderDetailsStr = orderDetails.stream()
                                                   .map(orderDetail ->
                                                                String.format("%s * %s; ",
                                                                              orderDetail.getName(),
                                                                              orderDetail.getAmount())
                                                   )
                                                   .toList();
        // 返回示例: 宫保鸡丁 * 3; 麻辣鸡 * 4;
        return StrUtil.join("", orderDetailsStr);
    }

    private List<OrderVO> convertToOrderVO(List<Order> orders) {
        ArrayList<OrderVO> orderVOS = new ArrayList<>();

        if (orders == null || orders.isEmpty())
            return orderVOS;

        orders.forEach(order -> {
            OrderVO orderVO = BeanUtil.copyProperties(order, OrderVO.class);

            String orderDishesStr = getOrderDishesStr(order.getId());
            orderVO.setOrderDishes(orderDishesStr);

            orderVOS.add(orderVO);
        });
        return orderVOS;
    }

    private Order createOrder(OrderDTO orderDTO, AddressBook addressBook, Long userId) {
        Order order = BeanUtil.copyProperties(orderDTO, Order.class);
        order.setAddressBookId(addressBook.getId());
        order.setPhone(addressBook.getPhone());
        order.setAddress(addressBook.getDetail());
        order.setConsignee(addressBook.getConsignee());
        order.setNumber(String.valueOf(System.currentTimeMillis()));
        order.setUserId(userId);
        order.setStatus(OrderConst.PENDING_PAYMENT);
        order.setPayStatus(PayStatusConst.UNPAID);
        order.setOrderTime(LocalDateTime.now());
        return order;
    }

    private void shopCartNotContainsShops(List<ShopCart> list) {
        //  全局异常处理器处理
        if (list == null || list.isEmpty())
            throw new NotContainShopsException(MessageConst.SHOPPING_CART_EMPTY);
    }

    private void addressBookIsNull(AddressBook addressBook) {
        if (addressBook == null)
            throw new RuntimeException(MessageConst.ADDRESS_BOOK_IS_NULL);
    }

    private void cleanShopCart(Long userId) {
        shopCartMapper.deleteByUserId(userId);
    }
}
