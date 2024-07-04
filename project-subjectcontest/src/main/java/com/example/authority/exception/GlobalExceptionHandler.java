package com.example.authority.exception;


import com.example.authority.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author wangjy
 * @version 1.0
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public Result handle(AuthException ex){
        return Result.error(ex.getCode(),ex.getMessage());
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handle(Exception ex){
        return Result.error("500",ex.getMessage());
    }

}
