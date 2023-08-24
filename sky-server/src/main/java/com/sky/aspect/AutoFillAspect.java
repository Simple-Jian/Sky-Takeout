package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义填充的切面类
 * 它的作用是自动填充createTime,createUser等字段
 */
@Component
@Slf4j
@Aspect
public class AutoFillAspect {
    //指定切入点
    @Pointcut("execution(* com.sky.mapper.*.*(..))&& @annotation(com.sky.annotation.AutoFill)")
    public void autofillPointCut() {
    }

    ;

    /**
     * 前置通知
     */
    @Before("autofillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充...");
        //获取注解的操作类型
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();//方法的签名对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);  //获取注解对象
        OperationType operationType = autoFill.value();    //获取注解定义的操作类型
        //获取到当前被拦截方法传入的实体对象
        Object[] args = joinPoint.getArgs();        //获得所有参数
        Object entity = args[0];                       //获得需要的实体对象(默认第一个参数)
        //获取需要赋值的数据
        Long currentId = BaseContext.getCurrentId();    //创建 or 更改人id
        LocalDateTime now = LocalDateTime.now();        //创建 or 更改时间

        //根据操作类型,通过反射进行赋值,即自动填充
        try {
            if (operationType == OperationType.INSERT) {
                //使用反射获取setter方法,并调用
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);
                Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);

                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            } else if (operationType == OperationType.UPDATE) {
                Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}