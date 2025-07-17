package com.takeout.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeePageQueryDTO implements Serializable {
    String name; // 员工名称
    int page; // 页面编号, 表示从第几页开始查询
    int pageSize; // 单页大小
}
