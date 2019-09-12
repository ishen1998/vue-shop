package com.shop.config;

import com.shop.componet.JwtAuthenticationTokenFilter;
import com.shop.componet.NoOpPasswordEncoder;
import com.shop.componet.RestAuthenticationEntryPoint;
import com.shop.componet.RestfulAccessDeniedHandler;
import com.shop.module.admin.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    RestfulAccessDeniedHandler restfulAccessDeniedHandler;
    @Autowired
    RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.
                csrf()// 由于使用的是JWT，我们这里不需要csrf
                .disable()//删除上面的以禁用
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()//不需要session
                .authorizeRequests()
                .antMatchers(
                        "/webjars/**",
                        "/resources/**",
                        "/doc.html",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/springfox-swagger-ui/css/typography.css",
                        "/v2/api-docs","/downloadFile/*","/uploadFile","/uploadMultipleFiles").permitAll()
                .antMatchers("/sysuser/login", "/sysuser/insert","/register","/testlogin","/checkToken","/sysuser/role/AllSysRole","/sysuser/checkToken/**",
                "/sysuser/role/permission",
                        //找回密码
                        "/findPassword",
                        "/getVeriCodeByEmail",
                        //商品
                        "/shop/findAllShopInfo","/shop/selectByPrimaryKey",
                        "/shop/selectItemInfoList","/shop/getShopName",
                        "/shop/getShopCategoryName","/shop/getShopLocality",
                        "/shop/selectItemInfoShop","/shop/shopLocalityImg",
                        "/shop/shopReturnImg",
                        //分类
                        "/category/**",
                        //活动
                        "/activity/**",
                        "/collection/**",
                        //卡卷
                        "/ticket/**",
                        "/Trolly/**"
                )// 允许匿名访问
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)//跨域请求会先进行一次options请求
                .permitAll()
//                .antMatchers("/**")//测试时全部运行访问
//                .permitAll()
                .anyRequest()// 除上面外的所有请求全部需要鉴权认证
                .authenticated()
                .and()
                .cors()
                .and()
                .csrf().disable();
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        // 添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和未登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)//用户没有权限返回
                .authenticationEntryPoint(restAuthenticationEntryPoint);//用户Token校验失败返回
    }


    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

    @Override
    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }


    /**
     * 装载BCrypt密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new NoOpPasswordEncoder();
    }

    /**
     * 允许跨域调用的过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.setAllowCredentials(true);
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return new CorsFilter(source);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


}
