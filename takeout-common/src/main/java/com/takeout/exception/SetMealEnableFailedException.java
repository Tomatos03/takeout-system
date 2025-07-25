package com.takeout.exception;

import com.thoughtworks.xstream.core.BaseException;

/**
 * 套餐启用失败异常
 */
public class SetMealEnableFailedException extends BaseException {
    public SetMealEnableFailedException(String message) {
        super(message);
    }
}
