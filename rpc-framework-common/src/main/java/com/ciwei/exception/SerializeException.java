package com.ciwei.exception;


/**
 * Create by LzWei on 2025/4/9
 */
public class SerializeException extends RuntimeException {
    public SerializeException(String message) {
        super(message);
    }

    public SerializeException(String message,Throwable cause) {
        super(message,cause);
    }
}
