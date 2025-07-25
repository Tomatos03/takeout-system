package com.takeout.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    private Long id;
    private String name;
    private String image;
    private Long orderId;
    private Long dishId;
    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private Double amount;
}
