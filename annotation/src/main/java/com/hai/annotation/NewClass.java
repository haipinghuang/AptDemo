package com.hai.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ${desc}
 * create by huanghp at 2018/6/20
 * email 1132760021@qq.com
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface NewClass {
    int value() default 0;
}
