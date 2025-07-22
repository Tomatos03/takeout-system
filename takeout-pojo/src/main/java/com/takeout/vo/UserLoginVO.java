package com.takeout.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginVO {
    private String id;
    private String openid;
    private String token;
}
