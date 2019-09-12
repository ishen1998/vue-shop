package com.shop.componet;


import com.shop.module.admin.entity.user.SysUser;
import com.shop.module.admin.service.SysUserService;
import com.shop.module.admin.service.impl.UserDetailsServiceImpl;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.entity.CustomerInfoDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/**
 * JWT登入授权过滤器
 *
 * @date 2019/5/012 9:27
 */
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsService userDetailsService;
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException, AuthenticationException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null) {
            String token = authHeader.substring(this.tokenHead.length());
            if (jwtTokenUtil.validateToken(token)) {
                UserDetails userDetails = jwtTokenUtil.generateAdminDetails(token);
                if (userDetails==null){
                    CustomerInfo customerInfo = jwtTokenUtil.getCustomerFromToken(token);
                    Object o = redisTemplate.opsForValue().get("Customer:" + customerInfo.getcId());
                    if (o==null){
                        chain.doFilter(request, response);
                        return;
                    }
                    String t=o.toString();
                    if (!token.trim().equals(t)){
                        chain.doFilter(request, response);
                        return;
                    }
                    UserDetails details = new CustomerInfoDetails(customerInfo);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(details, null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    chain.doFilter(request, response);
                    return;
                }
                userDetails = userDetailsService.loadUserByUsername(userDetails.getUsername());
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);

    }

    public void handelCustomer(){

    }
}