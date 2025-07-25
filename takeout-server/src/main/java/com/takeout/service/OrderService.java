package com.takeout.service;

import com.takeout.dto.OrderConfirmDTO;
import com.takeout.dto.OrderDTO;
import com.takeout.dto.OrderCancelDTO;
import com.takeout.dto.OrdersRejectionDTO;
import com.takeout.dto.page.OrdersPageQueryDTO;
import com.takeout.util.PageResult;
import com.takeout.vo.OrderStatisticsVO;
import com.takeout.vo.OrderSubmitVO;
import com.takeout.vo.OrderVO;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
public interface OrderService {
    OrderSubmitVO submitOrder(OrderDTO orderDTO);

    void reminder(Long id);

    PageResult<OrderVO> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO);

    OrderStatisticsVO statistics();

    OrderVO details(Long id);

    void confirm(OrderConfirmDTO orderConfirmDTO);

    void rejection(OrdersRejectionDTO ordersRejectionDTO);

    void cancel(OrderCancelDTO ordersCancelDTO);

    void delivery(Long id);

    void complete(Long id);
}
