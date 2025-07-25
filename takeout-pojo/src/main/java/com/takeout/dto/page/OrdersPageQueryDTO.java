package com.takeout.dto.page;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/24
 */
public class OrdersPageQueryDTO extends PageQueryDTO {
    private String number;
    private  String phone;
    private Integer status;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    private Long userId;
}
