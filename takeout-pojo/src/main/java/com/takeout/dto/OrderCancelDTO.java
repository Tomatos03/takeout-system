package com.takeout.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderCancelDTO implements Serializable {
    private Long id;
    //订单取消原因
    private String cancelReason;
}
