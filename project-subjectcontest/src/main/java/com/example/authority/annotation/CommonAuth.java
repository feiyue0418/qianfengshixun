package com.example.authority.annotation;

import java.lang.annotation.*;

/**
 * 用户登录以后就可以访问的权限
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CommonAuth {
}
