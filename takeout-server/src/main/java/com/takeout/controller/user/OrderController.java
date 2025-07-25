package com.takeout.controller.user;

import com.takeout.dto.OrderDTO;
import com.takeout.service.OrderService;
import com.takeout.util.Result;
import com.takeout.vo.OrderSubmitVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Slf4j
@RestController("userOrderController")
@RequestMapping("/user/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/submit")
    public Result<OrderSubmitVO> OrderSubmit(OrderDTO orderDTO) {
        OrderSubmitVO orderVO = orderService.submitOrder(orderDTO);
        return Result.success(orderVO);
    }

    @GetMapping("/reminder/{id}")
    public Result reminder(@PathVariable Long id) {
        orderService.reminder(id);
        return Result.success();
    }
}
