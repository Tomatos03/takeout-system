package com.takeout.handler;

import com.takeout.constant.MessageConst;
import com.takeout.exception.DeletionNotAllowedException;
import com.takeout.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.channels.NotYetConnectedException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author : Tomatos
 * @date : 2025/7/18
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
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

    @ExceptionHandler
    public Result handleDeletionNotAllowed(DeletionNotAllowedException ex) {
        log.warn("删除操作被禁止: {}", ex.getMessage());
        // 你可以自定义错误码和消息
        return Result.error(MessageConst.DELETION_NOT_ALLOWED);
    }

    @ExceptionHandler
    public Result handledNotContainsShops(NotYetConnectedException ex) {
        log.warn("购物车不包含任何商品");
        return Result.error(MessageConst.SHOPPING_CART_EMPTY);
    }
}
