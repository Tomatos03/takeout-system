package com.takeout.handler;

import com.takeout.constant.MessageConst;
import com.takeout.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
@Slf4j
@ControllerAdvice
public class SQLHandler {
    @ExceptionHandler
    public Result handleUserAlreadyHas(SQLIntegrityConstraintViolationException ex) {
        String errorMessage = ex.getMessage();
        log.error("异常消息: {}", errorMessage);

        if (errorMessage.contains("Duplicate entry")) {
            return Result.error(MessageConst.USER_ALREADY_EXIST);
        }
        boolean isDuplicateError = errorMessage.contains("Duplicate entry");
        return Result.error(isDuplicateError ? MessageConst.USER_ALREADY_EXIST : MessageConst.UNKNOW_ERROR);
    }
}
