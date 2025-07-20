package com.takeout.exception;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
public class DeletionNotAllowedException extends RuntimeException {
    public DeletionNotAllowedException(String message) {
        super(message);
    }
}
