package com.cto.edu.common.repository.annotation;

import javax.persistence.criteria.JoinType;
import java.lang.annotation.*;

/**
 * User:cxtww
 * Date:2016年11月4日 下午4:47:29
 * Version:1.0
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QueryJoin {

    /**
     * 连接的名字
     * @return
     */
    String property();

    JoinType joinType();

}
