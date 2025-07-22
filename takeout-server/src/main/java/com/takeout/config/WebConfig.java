package com.takeout.config;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
import com.takeout.interceptor.JwsInterceptor;
import com.takeout.interceptor.UserJwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private JwsInterceptor adminJwtInterceptor;

    @Autowired
    private UserJwtInterceptor userJwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminJwtInterceptor)
                .addPathPatterns("admin/**")
                .excludePathPatterns("/admin/employee/login");

        registry.addInterceptor(userJwtInterceptor)
                .addPathPatterns("user/**")
                .excludePathPatterns("/user/user/login")
                .excludePathPatterns("/user/shop/status");
    }
}
