package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.constant.AccountStatusConst;
import com.takeout.dto.EmployeeDTO;
import com.takeout.entity.Employee;
import com.takeout.mapper.EmployeeMapper;
import com.takeout.service.EmployeeService;
import com.takeout.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 
 * 
 * @author : Tomatos
 * @date : 2025/7/17
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    @Override
    public void add(EmployeeDTO employeeDTO) {
        log.info("添加新员工: {}", employeeDTO);

        Employee employee = new Employee();
        BeanUtil.copyProperties(employeeDTO, employee);

        // 其他默认信息
        employee.setStatus(AccountStatusConst.ENABLE);
        // 设置默认密码并使用BCrypt加密
        String defaultEncryptedPassword = PasswordUtil.encode("123456");
        employee.setPassword(defaultEncryptedPassword);
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        // TODO 暂时设置最后修改人ID 和 创建人ID都为1
        employee.setUpdateUser(1L);
        employee.setCreateUser(1L);

        employeeMapper.add(employee);
    }

    @Override
    public Employee login(EmployeeDTO employeeDTO) {
        log.info("正在登录:{}", employeeDTO);

        // 判断用户是否存在
        String username = employeeDTO.getUsername();
        Employee employee  = employeeMapper.getEmployeeByUsername(username);
        if (employee == null) {
            return null;
        }

        // 验证密码是否一致
        String InputPassword = employeeDTO.getPassword();
        String encodedPassword = employee.getPassword();
        if (!PasswordUtil.matches(InputPassword, encodedPassword)) {
            return null;
        }

        // 验证账户状态
        if (employee.getStatus() == AccountStatusConst.DISABLE) {
            return null;
        }

        return employee;
    }

    @Override
    public Employee getEmployeeByUsername(String username) {
        Employee employee = employeeMapper.getEmployeeByUsername(username);
        return employee;
    }
}
