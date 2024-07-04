package com.example.authority.annotation;

import java.lang.annotation.*;

/**
 * 不需要拦截的请求，什么时候都可以访问
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuth {
}
