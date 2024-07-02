package com.ziru.controller;

import com.ziru.common.Result;
import com.ziru.exception.CustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 */
@ControllerAdvice(basePackages="com.ziru.controller")
public class GlobalExceptionHandler {

    /**
     * 统一异常处理@ExceptionHandler,主要用于Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody//返回json串
    public Result error(Exception e){
        return Result.error(e.getMessage());
    }

    /**
     * 处理自定义的异常，自己抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody//返回json串
    public Result customError(CustomException e){
        return Result.error(e.getMsg());
    }

}

