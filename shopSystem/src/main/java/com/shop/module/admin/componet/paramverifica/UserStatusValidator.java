package com.shop.module.admin.componet.paramverifica;

import com.shop.module.admin.componet.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 12:07
 */
public class UserStatusValidator implements ConstraintValidator<CheckSatus,String> {

    private boolean required =false;

    @Override
    public void initialize(CheckSatus constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(required){
            return ValidatorUtil.checkUserAdminStatus(value);
        }else {
            return false;
        }
    }
}
