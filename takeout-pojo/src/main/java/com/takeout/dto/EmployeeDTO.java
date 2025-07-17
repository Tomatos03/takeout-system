package com.takeout.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * @author : Tomatos
 * @date : 2025/7/17
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EmployeeDTO {
    private Long id;
    // 账户
    private String username;
    // 员工姓名
    private String name;
    // 手机号
    private String phone;
    // 性别
    private String sex;
    // 身份证号码
    private String idNumber;
    private String password;
}