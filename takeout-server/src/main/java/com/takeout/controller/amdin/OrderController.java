package com.takeout.controller.amdin;

import com.takeout.dto.OrderConfirmDTO;
import com.takeout.dto.OrderCancelDTO;
import com.takeout.dto.OrdersRejectionDTO;
import com.takeout.dto.page.OrdersPageQueryDTO;
import com.takeout.service.OrderService;
import com.takeout.util.PageResult;
import com.takeout.util.Result;
import com.takeout.vo.OrderStatisticsVO;
import com.takeout.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Tomatos
 * @date : 2025/7/24
 */
@Slf4j
@RestController("adminOrderController")
@RequestMapping("/admin/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/conditionSearch")
    public Result<PageResult<OrderVO>> conditionSearch(OrdersPageQueryDTO ordersPageQueryDTO) {
        PageResult<OrderVO> pageResult = orderService.conditionSearch(ordersPageQueryDTO);
        return Result.success(pageResult);
    }

    @GetMapping("/statistics")
    public Result<OrderStatisticsVO> statistics() {
        OrderStatisticsVO orderStatisticsVO = orderService.statistics();
        return Result.success(orderStatisticsVO);
    }

    @GetMapping("/details/{id}")
    public Result<OrderVO> details(@PathVariable Long id) {
        OrderVO details = orderService.details(id);
        return Result.success(details);
    }

    @PutMapping("/confirm")
    public Result confirm(@RequestBody OrderConfirmDTO orderConfirmDTO) {
        orderService.confirm(orderConfirmDTO);
        return Result.success();
    }

    @PutMapping("/rejection")
    public Result rejection(@RequestBody OrdersRejectionDTO ordersRejectionDTO) throws Exception {
        orderService.rejection(ordersRejectionDTO);
        return Result.success();
    }

    @PutMapping("/cancel")
    public Result cancel(@RequestBody OrderCancelDTO ordersCancelDTO) throws Exception {
        orderService.cancel(ordersCancelDTO);
        return Result.success();
    }


    /**
     * 派送订单
     *
     * @return
     */
    @PutMapping("/delivery/{id}")
    public Result delivery(@PathVariable Long id) {
        orderService.delivery(id);
        return Result.success();
    }

    /**
     * 完成订单
     * @param id
     * @return
     */
    @PutMapping("/complete/{id}")
    public Result complete(@PathVariable Long id) {
        orderService.complete(id);
        return Result.success();
    }
}
