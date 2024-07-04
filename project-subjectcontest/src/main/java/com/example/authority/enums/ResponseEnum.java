package com.example.authority.enums;

public enum ResponseEnum {

    SUCCESS("200","成功"),
    ERROR("500","系统异常"),
    NOAUTH("401","没有权限"),
    NOAUTHORITY("402","你没有后台操作接口权限");

    private String code;

    private String msg;

    private ResponseEnum(String code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
