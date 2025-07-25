package com.takeout.task;

import com.takeout.constant.MessageConst;
import com.takeout.constant.OrderConst;
import com.takeout.entity.Order;
import com.takeout.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Slf4j
@Component
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 支付超时订单处理
     * 对于下单后超过15分钟仍未支付的订单自动修改状态为 [已取消]
     */
    @Scheduled(cron = "0 * * * * ?")
    public void processTimeoutOrder() {
//        log.info("开始进行支付超时订单处理:{}", LocalDateTime.now());
        LocalDateTime currentTime = LocalDateTime.now()
                                                 .minusMinutes(15);
        List<Order> overdueOrders = orderMapper.getOverdueOrders(OrderConst.PENDING_PAYMENT, currentTime);
        if (overdueOrders == null || overdueOrders.isEmpty())
            return;

        overdueOrders.forEach((order) -> {
            log.info("处理超时订单{}", order.getId());
            order.setStatus(OrderConst.CANCELLED);
            order.setCancelReason(MessageConst.ORDER_TIMEOUT_CANCEL);
            order.setCancelTime(LocalDateTime.now());

            orderMapper.update(order);
        });
    }

    /**
     * 派送中状态的订单处理
     * 对于一直处于派送中状态的订单，自动修改状态为 [已完成]
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void processDeliveryOrder(){
        log.info("开始进行未完成订单状态处理:{}", LocalDateTime.now());

        LocalDateTime currentTime = LocalDateTime.now()
                                                 .minusHours(1);
        List<Order> overdueOrders = orderMapper.getOverdueOrders(OrderConst.DELIVERING, currentTime);
        if (overdueOrders == null || overdueOrders.isEmpty())
            return;

        overdueOrders.forEach((order) -> {
            order.setStatus(OrderConst.COMPLETED);

            orderMapper.update(order);
        });
    }
}