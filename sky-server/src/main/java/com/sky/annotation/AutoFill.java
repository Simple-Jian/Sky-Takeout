package com.sky.annotation;

import com.sky.enumeration.OperationType;
import jdk.dynalink.Operation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 一个自动填充的标注注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    //通过一个枚举类,指定数据库操作类型,即更新和插入需要自动填充
    OperationType value();
}
