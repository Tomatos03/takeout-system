package com.takeout.service;

import com.takeout.dto.EmployeeDTO;
import com.takeout.dto.EmployeePageQueryDTO;
import com.takeout.entity.Employee;
import com.takeout.util.PageResult;

/**
 * 
 * 
 * @author : Tomatos
 * @date : 2025/7/17
 */
public interface EmployeeService {
    void add(EmployeeDTO employeeDTO);

    Employee login(EmployeeDTO employeeDTO);

    PageResult<Employee> pageQuery(EmployeePageQueryDTO pageQueryDTO);

    void startOrStop(Integer status, Long id);

    int updateInfo(EmployeeDTO employeeDTO);

    Employee getEmployeeById(Long id);
}
