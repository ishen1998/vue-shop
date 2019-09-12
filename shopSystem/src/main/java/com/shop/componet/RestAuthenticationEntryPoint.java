package com.shop.componet;

import com.shop.module.admin.result.CommonResult;
import cn.hutool.json.JSONUtil;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 当未登录或者token失效访问接口时，自定义的返回结果
 * 2019/5/14.
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        CommonResult<String> unauthorized = CommonResult.unauthorized(authException.getMessage());
        if (authException.getClass()==DisabledException.class){
            unauthorized = CommonResult.unauthorized("该账户已被禁用");
            response.setStatus(403);
        }
        response.getWriter().println(JSONUtil.parse(unauthorized));
        response.getWriter().flush();
//        authException.printStackTrace();
    }
}
