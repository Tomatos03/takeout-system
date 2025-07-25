package com.takeout.exception;

import com.thoughtworks.xstream.core.BaseException;

public class OrderBusinessException extends BaseException {

    public OrderBusinessException(String msg) {
        super(msg);
    }

}
