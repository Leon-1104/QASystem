package com.leon.common;

import lombok.Data;

/**
 * 接口统一返回包装类
 */
@Data
public class Result {
    //定义返回值类型
    private Boolean flag;
    private Object data;
    private String msg;
    private String code;

    public Result(String msg) {
        this.flag = false;
        this.msg = msg;
    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result() {
    }

    public Result(Boolean flag) {
        this.flag = flag;
    }

    public Result(Boolean flag, Object data) {
        this.flag = flag;
        this.data = data;
    }

    public Result(Boolean flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }
}
