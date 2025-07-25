package com.takeout.controller.amdin;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.constant.JwsClaimConst;
import com.takeout.dto.EmployeeDTO;
import com.takeout.dto.page.EmployeePageQueryDTO;
import com.takeout.entity.Employee;
import com.takeout.model.Payload;
import com.takeout.properties.JwsProperties;
import com.takeout.service.EmployeeService;
import com.takeout.util.JwsUtil;
import com.takeout.util.PageResult;
import com.takeout.util.Result;
import com.takeout.vo.EmployeeLoginVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Result add(@RequestBody EmployeeDTO employee) {
        employeeService.add(employee);
        return Result.success();
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

        JwsProperties.User jwtUser = jwsProperties.getUser();
        String token = JwsUtil.createJws(jwtUser.getExpiration(), jwtUser.getSecretKey(), payload);
        employeeLoginVO.setToken(token);

        return Result.success(employeeLoginVO);
    }

    @GetMapping("/page")
    // springBoot路径参数查询自动绑定到pageQueryDTO的属性
    public Result<PageResult<Employee>> pageQuery(EmployeePageQueryDTO pageQueryDTO) {
        PageResult<Employee> result = employeeService.pageQuery(pageQueryDTO);
        return Result.success(result);
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("启用禁用员工账户：{}，{}", status, id);
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Employee> getEmployeeById(@PathVariable Long id){
        log.info("查询员工{}的信息", id);
        Employee employee = employeeService.getEmployeeById(id);
        return Result.success(employee);
    }


    @PutMapping
    public Result update(@RequestBody EmployeeDTO employeeDTO) {
        log.info("更新员工{}的信息", employeeDTO.getId());
        // 受影响的行数
        int row = employeeService.updateInfo(employeeDTO);
        return row == 0 ? Result.error("更新失败") : Result.success();
    }
}