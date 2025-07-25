package com.takeout.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressBook {
    private Long id;
    private Long userId;
    private String consignee;
    private String sex;
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
}
