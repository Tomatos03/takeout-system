package com.takeout.service.impl;

import com.takeout.constant.DishStatusConst;
import com.takeout.constant.OrderConst;
import com.takeout.constant.SetMealStatusConst;
import com.takeout.mapper.DishMapper;
import com.takeout.mapper.OrderMapper;
import com.takeout.mapper.SetMealMapper;
import com.takeout.mapper.UserMapper;
import com.takeout.service.WorkspaceService;
import com.takeout.vo.BusinessDataVO;
import com.takeout.vo.DishOverViewVO;
import com.takeout.vo.OrderOverViewVO;
import com.takeout.vo.SetMealOverViewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WorkspaceServiceImpl implements WorkspaceService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private SetMealMapper setmealMapper;

    /**
     * 根据时间段统计营业数据
     * @param begin
     * @param end
     * @return
     */
    public BusinessDataVO getBusinessData(LocalDateTime begin, LocalDateTime end) {
        /**
         * 营业额：当日已完成订单的总金额
         * 有效订单：当日已完成订单的数量
         * 订单完成率：有效订单数 / 总订单数
         * 平均客单价：营业额 / 有效订单数
         * 新增用户：当日新增用户的数量
         */

        Map map = new HashMap();
        map.put("begin",begin);
        map.put("end",end);

        //查询总订单数
        Integer totalOrderCount = orderMapper.queryOrderNumByMap(map);

        map.put("status", OrderConst.COMPLETED);
        //营业额
        Double turnover = orderMapper.queryTurnoverByMap(map);
        turnover = turnover == null? 0.0 : turnover;

        //有效订单数
        Integer validOrderCount = orderMapper.queryOrderNumByMap(map);

        Double unitPrice = 0.0;
        Double orderCompletionRate = 0.0;
        if(totalOrderCount != 0 && validOrderCount != 0){
            //订单完成率
            orderCompletionRate = validOrderCount.doubleValue() / totalOrderCount;
            //平均客单价
            unitPrice = turnover / validOrderCount;
        }

        //新增用户数
        Integer newUsers = userMapper.countByMap(map);
        return BusinessDataVO.builder()
                .turnover(turnover)
                .validOrderCount(validOrderCount)
                .orderCompletionRate(orderCompletionRate)
                .unitPrice(unitPrice)
                .newUsers(newUsers)
                .build();
    }


    /**
     * 查询订单管理数据
     *
     * @return
     */
    public OrderOverViewVO getOrderOverView() {
        Map map = new HashMap();
        map.put("begin", LocalDateTime.now().with(LocalTime.MIN));
        map.put("status", OrderConst.PENDING_ACCEPT);

        //待接单
        Integer waitingOrders = orderMapper.queryOrderNumByMap(map);

        //待派送
        map.put("status", OrderConst.ACCEPTED);
        Integer deliveredOrders = orderMapper.queryOrderNumByMap(map);

        //已完成
        map.put("status", OrderConst.COMPLETED);
        Integer completedOrders = orderMapper.queryOrderNumByMap(map);

        //已取消
        map.put("status", OrderConst.CANCELLED);
        Integer cancelledOrders = orderMapper.queryOrderNumByMap(map);

        //全部订单
        map.put("status", null);
        Integer allOrders = orderMapper.queryOrderNumByMap(map);

        return OrderOverViewVO.builder()
                .waitingOrders(waitingOrders)
                .deliveredOrders(deliveredOrders)
                .completedOrders(completedOrders)
                .cancelledOrders(cancelledOrders)
                .allOrders(allOrders)
                .build();
    }

    /**
     * 查询菜品总览
     *
     * @return
     */
    public DishOverViewVO getDishOverView() {
        Map map = new HashMap();
        map.put("status", DishStatusConst.START_SELL);
        Integer sold = dishMapper.countByMap(map);

        map.put("status", DishStatusConst.STOP_SELL);
        Integer discontinued = dishMapper.countByMap(map);

        return DishOverViewVO.builder()
                .sold(sold)
                .discontinued(discontinued)
                .build();
    }

    /**
     * 查询套餐总览
     *
     * @return
     */
    public SetMealOverViewVO getSetMealOverView() {
        Map map = new HashMap();
        map.put("status", SetMealStatusConst.START_SELL);
        Integer sold = setmealMapper.countByMap(map);

        map.put("status", SetMealStatusConst.STOP_SELL);
        Integer discontinued = setmealMapper.countByMap(map);

        return SetMealOverViewVO.builder()
                                .sold(sold)
                                .discontinued(discontinued)
                                .build();
    }
}