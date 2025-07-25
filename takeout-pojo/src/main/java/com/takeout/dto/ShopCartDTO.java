package com.takeout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartDTO {
    private String dishFlavor;
    private Long dishId;
    private Long setmealId;
}
