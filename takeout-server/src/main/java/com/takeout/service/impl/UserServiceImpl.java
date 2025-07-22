package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.entity.User;
import com.takeout.mapper.UserMapper;
import com.takeout.properties.WeChatProperties;
import com.takeout.service.UserService;
import com.takeout.util.HttpClient5Util;
import com.takeout.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final String WECHAT_LOGIN_API = "https://api.weixin.qq.com/sns/jscode2session";

    @Autowired
    WeChatProperties weChatProperties;
    @Autowired
    UserMapper userMapper;

    @Override
    public UserLoginVO wechatLogin(String code) {
        String openId = getOpenId(code);
        if (openId == null)
            throw new RuntimeException("tmp");

        User user = tryRegisterNewUser(openId);
        UserLoginVO userLoginVO = BeanUtil.copyProperties(user, UserLoginVO.class);
        return userLoginVO;
    }

    private User tryRegisterNewUser(String openId) {
        User user = userMapper.queryById(openId);
        if (user != null)
            return user;

        user = new User();
        user.setOpenid(openId);
        user.setCreateTime(LocalDateTime.now());

        userMapper.insert(user);
        return user;
    }

    private String getOpenId(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("appid", weChatProperties.getAppid());
        params.put("secret", weChatProperties.getSecret());
        params.put("code", code);
        params.put("grant_type", "authorization_code");

        // 调用工具类，向微信接口服务发送请求
        try {
            String json = HttpClient5Util.doGet(WECHAT_LOGIN_API, params);
            log.info("微信登录返回结果：{}", json);
        } catch (Exception e) {
            log.error("发送Get请求异常{}", e.getMessage());
            return null;
        }
        // 解析json字符串
//        JSONObject jsonObject = JSON.parseObject(json);
//        String openid = jsonObject.getString("openid");
//        log.info("微信用户的openid为：{}", openid);
        String openId = "";
        return openId;
    }
}
