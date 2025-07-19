package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.takeout.constant.AccountStatusConst;
import com.takeout.dto.EmployeeDTO;
import com.takeout.dto.EmployeePageQueryDTO;
import com.takeout.entity.Employee;
import com.takeout.mapper.EmployeeMapper;
import com.takeout.service.EmployeeService;
import com.takeout.util.PageResult;
import com.takeout.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PageResult<Employee> pageQuery(EmployeePageQueryDTO pageQueryDTO) {
        int pageNum = pageQueryDTO.getPage();
        int pageSize = pageQueryDTO.getPageSize();
        String name = pageQueryDTO.getName();

        PageHelper.startPage(pageNum, pageSize);
        List<Employee> empList = employeeMapper.getEmployeeByName(name);
        PageInfo<Employee> pageInfo = new PageInfo<>(empList);

        PageResult<Employee> pageResult = new PageResult<>(pageInfo.getTotal(), pageInfo.getList());
        return pageResult;
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                                    .status(status)
                                    .id(id)
                                    .build();
        employeeMapper.update(employee);
    }

    @Override
    public int updateInfo(EmployeeDTO employeeDTO) {
        Employee employee = BeanUtil.copyProperties(employeeDTO, Employee.class);

        // 受影响的行数
        int row = employeeMapper.update(employee);
        return row;
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Employee employee = employeeMapper.getEmployeeById(id);
        employee.setPassword(null);
        return employee;
    }
}
