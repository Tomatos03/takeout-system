package com.takeout.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
@Getter
@AllArgsConstructor
public class Payload <T>{
    private Map<String, T> claims;

    public void addClaim(String key, T val) {
        claims.put(key, val);
    }
}
