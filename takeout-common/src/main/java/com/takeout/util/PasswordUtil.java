package com.takeout.util;

/**
 * @author : Tomatos
 * @date : 2025/7/17
 */
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    // 加密
    public static String encode(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * 验证明文密码是否与加密密码一致
     *
     * @param rawPassword  明文密码
     * @param encodedPassword 加密密码
     * @return boolean
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/7/18 13:48
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}
