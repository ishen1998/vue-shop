package com.shop.module.customerInfo.controller;

import com.shop.common.CommonResult;
import com.shop.common.Result;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.dao.CustomerInfoDao;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author zhoulanzhen
 * @date 2019/8/019 16:38
 */
@RestController
public class CustomerEmailController {
    @Autowired
    private EmailService emailService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CustomerInfoDao customerInfoDao;

    @GetMapping("/getVeriCodeByEmail")
    public Result sendVerificationCode(String email) {
        Result result = new Result();
        try {
            emailService.sendVerificationCode(email);
            result.setCode("200");
            result.setMsg("发送成功");
        } catch (Exception e) {
            result.setCode("500");
            result.setMsg("发送失败");
        }
        return result;
    }


    @ResponseBody
    @GetMapping("/findPassword")
    public CommonResult checkVerificationCode1(String email, String cLoginName, String code, String password) throws CustomizeExp {
        emailService.checkVerificationCode(email, cLoginName, code, password);
        customerInfoDao.updatePwdByCname(cLoginName, password);
        return CommonResult.success("成功");
    }
}
