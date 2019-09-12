package com.shop.componet;


import com.shop.module.admin.entity.user.AdminDetails;
import com.shop.module.admin.entity.user.SysUser;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.shop.module.customerInfo.entity.CustomerInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.util.*;


/**
 * @date 2019/5/012 11:04
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "UserId";
    private static final String CLAIM_KEY_CREATED = "created";
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    private SecretKey key;
    private Claims claimsLog;


    //初始化自定义密钥
    @PostConstruct
    private void initKey() {
        key = Keys.hmacShaKeyFor(StrUtil.bytes(secret));
    }

    /**
     * 根据负载生成JWT的Token
     */
    private String generateToken(Map<String, Object> claims) {

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationData())
//                .compressWith(CompressionCodecs.DEFLATE)压缩
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();


    }

    /**
     * 从token中获取JWT中的负载
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            claimsLog = claims;
        } catch (Exception e) {
            LOGGER.info("Token 失效 {}", e.getMessage());
        }
        return claims;
    }

    /**
     * 生成token的时间
     */
    private Date generateExpirationData() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }

    /**
     * Token 解析出username
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username = null;
        Claims claims = getClaimsFromToken(token);
        try {
            username = claims.getSubject();
        } catch (Exception e) {
            LOGGER.info("Token解析失败:{}", token);
            LOGGER.info("Token中username值为:{}", username);
        }
        return username;
    }


    /**
     * Token 解析出Customer
     *
     * @param token
     * @return
     */
    public CustomerInfo getCustomerFromToken(String token) {
        CustomerInfo customerInfo = new CustomerInfo();
        Claims claims = getClaimsFromToken(token);
        try {
            customerInfo.setcLoginName(claims.get("UserId").toString());
            customerInfo.setcId(claims.get("cId").toString());
        } catch (Exception e) {
            LOGGER.info("Token解析失败:{}", token);
            LOGGER.info("Token中username值为:{}", customerInfo.getcLoginName());
        }
        return customerInfo;
    }


    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String userName = getUserNameFromToken(token);
        return userName.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 验证token是否还有效
     *
     * @param token 客户端传入的token
     */
    public boolean validateToken(String token) {
        if (getClaimsFromToken(token) != null) {
            if (!isTokenExpired(token)) {
                LOGGER.info("Token 有效 UserID:{} 过期时间:{}", claimsLog.get(CLAIM_KEY_USERNAME), DateUtil.formatDateTime(claimsLog.getExpiration()));
                return true;
            }
        }
        return false;
    }

    /**
     * 判断token时间是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    public Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        AdminDetails adminDetails = (AdminDetails) userDetails;
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put("userName", adminDetails.getSysUserName());
        claims.put(CLAIM_KEY_CREATED, new Date());
        claims.put("permission", userDetails.getAuthorities());
        return generateToken(claims);
    }

    /**
     * 根据用户信息生成token
     */
    public String customergenerateToken(CustomerInfo customerInfo) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("cId", customerInfo.getcId());
        claims.put("group", "ordinary");
        claims.put("phone", customerInfo.getMobile());
        claims.put(CLAIM_KEY_USERNAME, customerInfo.getcLoginName());
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public UserDetails generateUserDetails(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        SysUser sysUser = new SysUser();
        sysUser.setUserName(claimsFromToken.get(CLAIM_KEY_USERNAME).toString());
        ArrayList permissions = claimsFromToken.get("role", ArrayList.class);
        ArrayList<String> pmi = new ArrayList<>();
        for (Object o : permissions) {
            LinkedHashMap map = (LinkedHashMap) o;
            pmi.add(map.get("authority").toString());
        }
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        for (int i = 0; i < pmi.size(); i++) {
            authList.add(new SimpleGrantedAuthority(pmi.get(i)));
        }

        return new AdminDetails(sysUser, authList);
    }

    public UserDetails generateAdminDetails(String token) {
        Claims claimsFromToken = getClaimsFromToken(token);
        try {
            Object group = claimsFromToken.get("group");
            if (group != null) {
                return null;
            }
        } catch (NullPointerException n) {
            return null;
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(claimsFromToken.get(CLAIM_KEY_USERNAME).toString());
        sysUser.setUserName(claimsFromToken.get("userName").toString());
        ArrayList arrayList = claimsFromToken.get("permission",ArrayList.class);
        ArrayList<String> pmi = new ArrayList<>();
        for (Object o : arrayList) {
            LinkedHashMap map = (LinkedHashMap) o;
            pmi.add(map.get("authority").toString());
        }
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(String.join(",", pmi
        ));

        return new AdminDetails(sysUser, grantedAuthorities);
    }

//    public CustomerInfo generateAdminDetails(String token) {
//        Claims claimsFromToken = getClaimsFromToken(token);
//        try {
//            claimsFromToken.get("group");
//        } catch (NullPointerException n) {
//            return null;
//        }
//        CustomerInfo customerInfo = new CustomerInfo();
//        customerInfo.setcLoginName();
//        return ;
//    }


    /**
     * 判断token是否可以被刷新
     */
    public boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    public String getSecret() {
        return secret;
    }

    public Long getExpiration() {
        return expiration;
    }


}
