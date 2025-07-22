package com.takeout.service;

import com.takeout.vo.UserLoginVO;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
public interface UserService {
    UserLoginVO wechatLogin(String code);
}
