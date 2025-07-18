package com.takeout.interceptor;

import com.takeout.constant.JwsClaimConst;
import com.takeout.context.EmployeeContext;
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
 * @date : 2025/7/18
 */
@Component
@Slf4j
public class JwsInterceptor implements HandlerInterceptor {
    @Autowired
    private JwsProperties jwsProperties;

    /*
     *  请求资源都需要验证是否持有Token, 持有正常放行, 不持有拦截并返回状态码401禁止访问
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Interceptor默认不拦截静态资源, 为了代码健壮性添加本行代码
        if (!(handler instanceof HandlerMethod)) {
            // 当前请求不是 Controller 方法（比如静态资源），直接放行
            return true;
        }

        log.info("尝试解析Token...");
        String tokenPrefix = jwsProperties.getPrefix();
        String token = request.getHeader(tokenPrefix);
        if (token == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        Jws<Claims> claimsJws;
        try {
            claimsJws = JwsUtil.parseVerifyJws(jwsProperties, token);
        } catch (JwtException ex) {
            log.info("解析Token失败");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return false;
        }

        log.info("解析Token成功");
        Claims payload = claimsJws.getPayload();
        Long empId = Long.valueOf((String) payload.get(JwsClaimConst.EMP_ID));
        EmployeeContext.setCurrentEmpId(empId);
        return true;
    }
}
