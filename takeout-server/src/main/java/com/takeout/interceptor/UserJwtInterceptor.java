package com.takeout.interceptor;


import com.takeout.constant.JwsClaimConst;
import com.takeout.context.LoginContext;
import com.takeout.properties.JwsProperties;
import com.takeout.util.JwsUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Slf4j
@Component
public class UserJwtInterceptor implements HandlerInterceptor {
    @Autowired
    JwsProperties jwsProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Interceptor默认不拦截静态资源, 为了代码健壮性添加本行代码
        if (!(handler instanceof HandlerMethod)) {
            // 当前请求不是 Controller 方法（比如静态资源），直接放行
            return true;
        }

        log.info("尝试解析Token...");
        JwsProperties.User jwsUser = jwsProperties.getUser();
        String tokenPrefix = jwsUser.getPrefix();
        String token = request.getHeader(tokenPrefix);
        if (token == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Jws<Claims> claimsJws;
        try {
            claimsJws = JwsUtil.parseVerifyJws(jwsUser.getSecretKey(), token);
        } catch (JwtException ex) {
            log.info("解析Token失败");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        log.info("解析Token成功");
        Claims payload = claimsJws.getPayload();
        Long empId = Long.valueOf((String) payload.get(JwsClaimConst.EMP_ID));
        LoginContext.setCurrentId(empId);
        return true;
    }
}
