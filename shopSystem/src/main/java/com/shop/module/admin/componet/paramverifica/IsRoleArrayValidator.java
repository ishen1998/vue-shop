package com.shop.module.admin.componet.paramverifica;

import com.shop.module.admin.componet.ValidatorUtil;
import com.shop.module.admin.componet.paramverifica.CustomCheck;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/001 12:07
 */
public class IsRoleArrayValidator implements ConstraintValidator<CustomCheck,List<String>> {

    private boolean required =false;

    @Override
    public void initialize(CustomCheck constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if(required){
            //调用第3步 中的校验类的校验方法进行判断是否是手机号
            return ValidatorUtil.checkArray(value);
        }else {
            return false;
        }
    }
}
