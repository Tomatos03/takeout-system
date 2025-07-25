package com.takeout.vo;

import com.takeout.entity.Order;
import com.takeout.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO extends Order {
    //订单菜品信息
    private String orderDishes;
    //订单详情
    private List<OrderDetail> orderDetailList;
}

