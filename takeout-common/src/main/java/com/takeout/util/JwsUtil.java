package com.takeout.util;

import com.takeout.model.Payload;
import com.takeout.properties.JwsProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.util.Date;

/**
 * @author : Tomatos
 * @date : 2025/7/17
 */
public class JwsUtil {
//    private final static SecretKey secretKey = Jwts.SIG.HS256.key().build();

    public static <T> String createJws(JwsProperties jwsProperties, Payload<T> payload) {
        if (jwsProperties == null || payload == null)
                throw new NullPointerException();

        // jwsProperties之中的过期时间单位是秒, 这里需要转换为毫秒
        long jwsExpiration = System.currentTimeMillis() + jwsProperties.getExpiration() * 1000L;
        return Jwts.builder()
                   .expiration(new Date(jwsExpiration))
                   .signWith(Keys.hmacShaKeyFor(jwsProperties.getSecretKey().getBytes()))
                   .claims(payload.getClaims())
                   .compact();
    }

//    public static String getSecretKeyStr() {
//        byte[] encoded = secretKey.getEncoded();
//        Base64.Encoder encoder = Base64.getEncoder();
//        return encoder.encodeToString(encoded);
//    }

    public static Jws<Claims> parseVerifyJws(JwsProperties jwsProperties, String jws) throws JwtException{
        return Jwts.parser()
                   .verifyWith(Keys.hmacShaKeyFor(jwsProperties.getSecretKey().getBytes()))
                   .build() // 构建一个使用secretKey验证JWS的Parser对象
                   .parseSignedClaims(jws); // 解析得到 payload
    }
}
