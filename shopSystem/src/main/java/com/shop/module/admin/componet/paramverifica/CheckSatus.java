package com.shop.module.admin.componet.paramverifica;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 12:05
 */
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER })
@Retention(RUNTIME)
@Documented
//校验类
@Constraint(validatedBy = {UserStatusValidator.class })

public @interface CheckSatus {
    //默认为true
    boolean required() default true;
    //提示信息
    String message() default "用户状态只能为Y/N";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
