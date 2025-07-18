package com.takeout.service;

import com.takeout.dto.EmployeeDTO;
import com.takeout.entity.Employee;

/**
 * 
 * 
 * @author : Tomatos
 * @date : 2025/7/17
 */
public interface EmployeeService {
    void add(EmployeeDTO employeeDTO);

    Employee login(EmployeeDTO employeeDTO);

    Employee getEmployeeByUsername(String username);
}
