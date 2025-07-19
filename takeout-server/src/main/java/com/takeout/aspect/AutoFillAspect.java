package com.takeout.aspect;

import com.takeout.annotation.AutoFill;
import com.takeout.constant.AutoFillConst;
import com.takeout.context.EmployeeContext;
import com.takeout.enums.SqlOperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Slf4j
@Aspect
@Component
public class AutoFillAspect {
    // @annotation(com.takeout.annotation.AutoFill) 限定只拦截有该注解的方法
    @Before("execution(* com.takeout.mapper.*.*(..)) && @annotation(com.takeout.annotation.AutoFill)")
    public void beforeAdvice(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        log.info("尝试自动填充字段");
        Object[] args = joinPoint.getArgs();
        if (!checkArgNum(args))
            return;


        Object entity = args[0];
        Class<?> classZ = entity.getClass();

        Long currentEmpId = EmployeeContext.getCurrentEmpId();
        LocalDateTime now = LocalDateTime.now();
        try {
            Method updateUser = classZ.getMethod(AutoFillConst.SET_UPDATE_USER, Long.class);
            Method updateTime = classZ.getMethod(AutoFillConst.SET_UPDATE_TIME, LocalDateTime.class);
            updateUser.invoke(entity, currentEmpId);
            updateTime.invoke(entity, now);

            SqlOperationType type = method.getAnnotation(AutoFill.class).value();
            if (type == SqlOperationType.ADD || type == SqlOperationType.INSERT) {
                Method createUser = classZ.getMethod(AutoFillConst.SET_CREATE_USER, Long.class);
                Method createTime = classZ.getMethod(AutoFillConst.SET_CREATE_TIME, LocalDateTime.class);
                createUser.invoke(entity, currentEmpId);
                createTime.invoke(entity, now);
            }
        } catch (Exception e) {
            log.error("自动填充字段失败: {}", e.getMessage());
            return;
        }
        log.info("自动填充字段成功");
    }

    private boolean checkArgNum(Object[] args) {
        int argNum = args.length;
        if (argNum != 1) {
            log.info("参数数量不合法");
            return false;
        }
        return true;
    }
}