package com.norm.timemall.app.base.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 限定使用范围--方法、字段
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
// 表明注解的生命周期，它在运行代码时可以通过反射获取到注解
@Retention(RetentionPolicy.RUNTIME)
// validatedBy：以指定该注解的校验逻辑
@Constraint(validatedBy = EnumCheckValidator.class)
public @interface EnumCheck {
    // 是否必填 默认是必填的
    boolean required() default true;

    // 验证失败的消息
    String message() default "枚举的验证失败";

    //分组的内容
    Class<?>[] groups() default {};

    // 错误验证的级别
    Class<? extends Payload>[] payload() default {};

    // 枚举的Class
    Class<? extends Enum<?>> enumClass();

    // 枚举中的验证方法
    String enumMethod() default "validation";
}

