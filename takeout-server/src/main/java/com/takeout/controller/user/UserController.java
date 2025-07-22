package com.takeout.controller.user;

import com.takeout.dto.UserLoginDTO;
import com.takeout.service.UserService;
import com.takeout.util.Result;
import com.takeout.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Slf4j
@RequestMapping("user/user")
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO) {
        log.info("用户登录code:{}", userLoginDTO.getCode());
        UserLoginVO userLoginVO = userService.wechatLogin(userLoginDTO.getCode());
        return Result.success(userLoginVO);
    }
}
