package com.cto.edu.common.repository.annotation;

import java.lang.annotation.*;

/**
 * 开启查询缓存
 * Version:1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableQueryCache {

    boolean value() default true;

}
