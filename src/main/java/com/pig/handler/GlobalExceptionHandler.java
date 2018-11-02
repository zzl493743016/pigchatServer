package com.pig.handler;

import com.pig.utils.ResponseCode;
import com.pig.utils.ResponseObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局统一异常处理器
 * @author Arthas
 * @create 2018/11/2
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseObj defultExcepitonHandler(HttpServletRequest request, Exception ex) {
        logger.error("{} Exception", request.getRequestURI(), ex);
        return ResponseObj.createBy(ResponseCode.SYSTEM_ERROR);
    }
}
