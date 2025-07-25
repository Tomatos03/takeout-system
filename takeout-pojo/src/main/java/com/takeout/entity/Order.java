package com.takeout.entity;

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
public class Order {
    private Long id;
    private String number;
    private Integer status;
    private Long userId;
    private Long addressBookId;
    private LocalDateTime orderTime;
    private LocalDateTime checkoutTime;
    private Integer payMethod;
    private Integer payStatus;
    private Double amount;
    private String remark;
    private String phone;
    private String address;
    private String userName;
    private String consignee;
    private String cancelReason;
    private String rejectionReason;
    private LocalDateTime cancelTime;
    private LocalDateTime estimatedDeliveryTime;
    private Integer deliveryStatus;
    private LocalDateTime deliveryTime;
    private Integer packAmount;
    private Integer tablewareNumber;
    private Integer tablewareStatus;
}
