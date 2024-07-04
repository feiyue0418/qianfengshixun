package com.example.authority.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable {
    private String code;

    private String msg;

    private Object data;

    public static Result success(){
        return new Result("200","执行成功",null);
    }
    public static Result success(Object data){
        return new Result("200","执行成功",data);
    }
    public static Result error(){
        return new Result("500","执行失败",null);
    }
    public static Result error(String code,String msg){
        return new Result(code,msg,null);
    }
}
