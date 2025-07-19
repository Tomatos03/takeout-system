package com.takeout.mapper;

import com.takeout.annotation.AutoFill;
import com.takeout.entity.Employee;
import com.takeout.enums.SqlOperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/17
 */
@Mapper
public interface EmployeeMapper {
    @AutoFill(SqlOperationType.ADD)
    void add(Employee employee);

    Employee getEmployeeByUsername(String username);

    List<Employee> getEmployeeByName(String name);

    @AutoFill(SqlOperationType.UPDATE)
    int update(Employee employee);

    Employee getEmployeeById(Long id);
}
