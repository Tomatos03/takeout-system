package com.takeout.config;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
import com.takeout.interceptor.JwsInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwsInterceptor jwsInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwsInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/admin/employee/login");
    }
}
