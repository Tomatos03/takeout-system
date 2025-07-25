package com.takeout.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSubmitVO {
    // 订单id
    private Long id;
    // 订单号
    private String orderNumber;
    // 订单金额
    private Double orderAmount;
    // 下单时间
    private LocalDateTime orderTime;
}


