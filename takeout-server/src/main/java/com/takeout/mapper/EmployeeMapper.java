package com.takeout.mapper;

import com.takeout.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/17
 */
@Mapper
public interface EmployeeMapper {
    void add(Employee employee);

    Employee getEmployeeByUsername(String username);

    List<Employee> getEmployeeByName(String name);

    int update(Employee employee);

    Employee getEmployeeById(Long id);
}
