package com.pig.utils;

/**
 * 接口返回响应码
 * @author Arthas
 * @create 2018/11/2
 */
public enum  ResponseCode {

    //
    SUCCESS(200, "SUCCESS"),
    //
    ERROR(400, "ERROR"),
    //
    NEED_LOGIN(401,"NEED_LOGIN"),
    //
    ILLEGAL_ARGUMENT(402, "ILLEGAL_ARGUMENT"),
    //
    SYSTEM_ERROR(500, "SYSTEM_ERROR");

    private final int code;
    private final String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}