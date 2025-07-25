package com.takeout.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
public class OrderDTO {
    // 地址簿id
    private Long addressBookId;
    // 付款方式
    private int payMethod;
    // 备注
    private String remark;
    // 预计送达时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime estimatedDeliveryTime;
    // 配送状态  1立即送出  0选择具体时间
    private Integer deliveryStatus;
    // 餐具数量
    private Integer tablewareNumber;
    // 餐具数量状态  1按餐量提供  0选择具体数量
    private Integer tablewareStatus;
    // 打包费
    private Integer packAmount;
    // 总金额
    private Double amount;
}
