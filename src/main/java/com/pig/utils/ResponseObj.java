package com.pig.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 统一返回对象
 * @author Arthas
 * @create 2018/11/2
 */
// 如果是null的对象，key也会消失
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ResponseObj<T> implements Serializable {

    private int status;
    private String msg;
    private T data;


    private ResponseObj(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    private ResponseObj(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static <T>ResponseObj<T> createBy(ResponseCode code, String msg, T data) {
        return new ResponseObj<T>(code.getCode(), StringUtils.isBlank(msg) ? code.getMsg() : msg, data);
    }

    public static <T>ResponseObj<T> createBy(ResponseCode code, String msg) {
        return new ResponseObj<T>(code.getCode(), StringUtils.isBlank(msg) ? code.getMsg() : msg);
    }

    public static <T>ResponseObj<T> createBy(ResponseCode code, T data) {
        return new ResponseObj<T>(code.getCode(), code.getMsg(), data);
    }

    public static <T>ResponseObj<T> createBy(ResponseCode code) {
        return new ResponseObj<T>(code.getCode(), code.getMsg());
    }


//    public static <T>ResponseObj<T> createBySuccess() {
//        return new ResponseObj<T>(ResponseCode.SUCCESS.getCode());
//    }
//
//
//
//
//    public static <T>ResponseObj<T> createBySuccessMessage(String msg) {
//        return new ResponseObj<T>(ResponseCode.SUCCESS.getCode(), msg);
//    }
//
//    public static <T>ResponseObj<T> createBySuccess(T data) {
//        return new ResponseObj<T>(ResponseCode.SUCCESS.getCode(), data);
//    }
//
//    public static <T>ResponseObj<T> createBySuccessMessage(String msg, T data) {
//        return new ResponseObj<T>(ResponseCode.SUCCESS.getCode(), msg, data);
//    }
//
//    public static <T>ResponseObj<T> createByError(String msg) {
//        return new ResponseObj<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
//    }
//
//    public static <T>ResponseObj<T> createByErrorMessage(String msg) {
//        return new ResponseObj<T>(ResponseCode.ERROR.getCode(), msg);
//    }
//    public static <T>ResponseObj<T> createByCodeErrorMessage(int code, String msg) {
//        return new ResponseObj<T>(code, msg);
//    }

}
