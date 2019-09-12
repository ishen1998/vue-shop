package com.shop.module.admin.webexceptionhandler;

import com.shop.common.CommonResult;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.shop.module.admin.componet.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


/**
 * @author zhoulanzhen
 * @date 2019/7/016 11:25
 */
@ControllerAdvice
@ResponseBody
public class ControllerExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);



    @InitBinder
    public void initBinder(WebDataBinder binder) {
        GenericConversionService genericConversionService = (GenericConversionService) binder.getConversionService();
        if (genericConversionService != null) {
            genericConversionService.addConverter(new DateConverter());
        }
    }

    @ExceptionHandler(value = CustomizeExp.class)
    public CommonResult unknownException(Exception e) {
        log.error(e.getMessage());
        return CommonResult.failed(e.getMessage(), null);
    }

    @ExceptionHandler(value = MySQLIntegrityConstraintViolationException.class)
    public CommonResult prException(Exception e) {
        log.error("主键重复");
        e.printStackTrace();
        return CommonResult.failed("用户名重复", null);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult handlerBindException(MethodArgumentNotValidException e){
        StringBuffer errorMsg = new StringBuffer();
        e.getBindingResult().getAllErrors().forEach(x -> errorMsg.append(x.getDefaultMessage()).append(","));
        return CommonResult.paramFailed("参数校验失败",errorMsg);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResult handlerBindException(ConstraintViolationException e){
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        String message = "未知的校验错误";
        for (ConstraintViolation<?> cons:constraintViolations ) {
            message = cons.getMessage();
        }
        return CommonResult.paramFailed("参数校验失败",message);
    }

}
