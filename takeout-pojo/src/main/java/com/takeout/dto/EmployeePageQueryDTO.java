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
public class EmployeePageQueryDTO extends PageQueryDTO implements Serializable {
    private String name; // 员工名称
}
