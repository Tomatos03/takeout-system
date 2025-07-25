package com.takeout.mapper;

import com.takeout.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Mapper
public interface UserMapper {
    User queryById(String openId);

    void insert(User user);

    Integer countByMap(Map map);
}
