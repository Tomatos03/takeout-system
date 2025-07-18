package com.takeout.controller;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.constant.JwsClaimConst;
import com.takeout.dto.EmployeeDTO;
import com.takeout.entity.Employee;
import com.takeout.model.Payload;
import com.takeout.properties.JwsProperties;
import com.takeout.service.EmployeeService;
import com.takeout.util.JwsUtil;
import com.takeout.util.Result;
import com.takeout.vo.EmployeeLoginVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author : Tomatos
 * @date : 2025/7/17
 */
@Slf4j
@RestController
@RequestMapping("/admin/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JwsProperties jwsProperties;

    @PostMapping
    public void add(@RequestBody EmployeeDTO employee) {
        employeeService.add(employee);
    }

    @PostMapping(value = "/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeDTO employeeDTO,
                                         HttpServletResponse response) {
        Employee employee = employeeService.login(employeeDTO);
        if (employee == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return Result.error("账号或密码错误");
        }

        EmployeeLoginVO employeeLoginVO = new EmployeeLoginVO();
        BeanUtil.copyProperties(employee, employeeLoginVO);

        Payload<String> payload = new Payload<>(new HashMap<>());
        payload.addClaim(JwsClaimConst.EMP_ID, employeeLoginVO.getId().toString());

        String token = JwsUtil.createJws(jwsProperties, payload);
        employeeLoginVO.setToken(token);

        return Result.success(employeeLoginVO);
    }
}