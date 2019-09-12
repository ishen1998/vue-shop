package com.shop.module.customerInfo.service;

import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.entity.FindPasswordEntity;

/**
 * @author zhoulanzhen
 * @date 2019/8/019 16:25
 */
public interface EmailService {
    String sendVerificationCode(String email) throws CustomizeExp;

    String checkVerificationCode(FindPasswordEntity entity) throws CustomizeExp;

    void checkVerificationCode(String email,String cLoginName, String code,String password) throws CustomizeExp;
}
