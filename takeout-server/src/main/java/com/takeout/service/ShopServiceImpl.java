package com.takeout.service;

import com.takeout.constant.RedisConst;
import com.takeout.constant.ShopStatusConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Slf4j
@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    RedisTemplate<String, Integer> redisTemplate;

    @Override
    public int status() {
        Integer res = redisTemplate.opsForValue()
                                 .get(RedisConst.SHOP_STATUS_KEY);
        if (res == null) {
            redisTemplate.opsForValue()
                         .set(RedisConst.SHOP_STATUS_KEY, 0);
            return ShopStatusConst.SHOP_CLOSED;
        }
        return res;
    }

    @Override
    public void setStatus(int status) {
        redisTemplate.opsForValue().set(RedisConst.SHOP_STATUS_KEY, status);
    }
}
