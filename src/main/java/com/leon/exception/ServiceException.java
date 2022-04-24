package com.leon.exception;

import lombok.Getter;

/**
 * 自定义异常
 * @author Leon
 */
@Getter
public class ServiceException extends RuntimeException {
    private String code;

    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
