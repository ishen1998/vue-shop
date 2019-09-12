package com.shop.module.customerInfo.service.Imp;

import cn.hutool.core.util.RandomUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.dao.CustomerInfoDao;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.entity.FindPasswordEntity;
import com.shop.module.customerInfo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author zhoulanzhen
 * @date 2019/8/019 16:26
 */
@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private CustomerInfoDao customerInfoDao;
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.from}")
    private String from;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public String sendVerificationCode(String email) throws CustomizeExp {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = RandomUtil.randomNumbers(6);
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Shop-找回密码");
        message.setText("你正在找回密码，你的验证码为:"+ code+"有效期5分钟");
        try {
            mailSender.send(message);
        } catch (MailException e) {
            e.printStackTrace();
            throw new CustomizeExp("邮件发送失败");
        }
        redisTemplate.opsForValue().set("EmailCode:"+email,code);
        redisTemplate.expire("EmailCode:"+email, 360, TimeUnit.SECONDS);
        return code;
    }

    @Override
    public String checkVerificationCode(FindPasswordEntity entity) throws CustomizeExp {
        return null;
    }

    @Override
    public void checkVerificationCode(String email,String cLoginName, String code,String password) throws CustomizeExp {
        CustomerInfo customerInfo = customerInfoDao.selectByLoginName(cLoginName);
        if (customerInfo == null) {
            throw new CustomizeExp("登录名不存在");
        }
        if (!customerInfo.getEmail().equals(email)) {
            throw new CustomizeExp("绑定邮箱错误");
        }
        Boolean flag = redisTemplate.hasKey("EmailCode:" + email);
        if(!flag){
            throw new CustomizeExp("验证码已过期");
        }
        String s = redisTemplate.opsForValue().get("EmailCode:" + email).toString();
        if (!code.equals(s) || code==null || code.isEmpty()) {
            throw new CustomizeExp("验证码错误");
        }
    }


}
