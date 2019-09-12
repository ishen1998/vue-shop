package com.shop.module.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.componet.JwtTokenUtil;
import com.shop.module.admin.dao.SysRoleDAO;
import com.shop.module.admin.dao.SysUserDao;
import com.shop.module.admin.dao.UserActivationMapper;
import com.shop.module.admin.dao.UserMapper;
import com.shop.module.admin.entity.ActivationCode;
import com.shop.module.admin.entity.role.SysRole;
import com.shop.module.admin.entity.user.*;
import com.shop.module.admin.service.SysUserService;
import com.shop.module.admin.util.SpringSecurityUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.shopcart.entity.TrolleyInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhoulanzhen
 * @date 2019/7/015 17:10
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private SysUserDao sysUserDAO;
    private SysRoleDAO sysRoleDAO;
    private UserMapper userMapper;
    @Autowired
    private UserActivationMapper userActivationMapper;
    @Autowired
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;
    private SpringSecurityUtil securityUtil;
    private static final String ENABLE = "Y";
    private static final String PROHIBIT = "N";
    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;



    @Autowired
    public SysUserServiceImpl(SysUserDao sysUserDAO, SysRoleDAO sysRoleDAO, UserMapper userMapper, JwtTokenUtil jwtTokenUtil, SpringSecurityUtil securityUtil) {
        this.sysUserDAO = sysUserDAO;
        this.sysRoleDAO = sysRoleDAO;
        this.userMapper = userMapper;
        this.jwtTokenUtil = jwtTokenUtil;
        this.securityUtil = securityUtil;
    }

    /**
     * 根据用户id删除 用户并清空角色关系
     * @param userId 用户id
     * @return 受影响的行数
     * @throws CustomizeExp
     */
    @CacheEvict(cacheNames = {"UserDetails","UserAuthorities"}, key = "#userId")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String userId) throws CustomizeExp {
        SysUser sysUser = sysUserDAO.selectByPrimaryKey(userId);
        if (ENABLE.equals(sysUser.getStatus())){
            throw new CustomizeExp("用户处于启用状态无法删除");
        }
        sysRoleDAO.deleteUserRoleByUserId(userId);
        int code = sysUserDAO.deleteByPrimaryKey(userId);
        if (code == 0) {
            throw new CustomizeExp("该用户不存在");
        }
        return code;
    }

    /**
     * 添加用户
     *
     * @param record 用户实体
     * @param roleIds 用户拥有的角色ID
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(SysUser record,String code, List<String> roleIds) throws CustomizeExp {
        if (roleIds == null || roleIds.size() == 0) {
            throw new CustomizeExp("角色不能为空");
        }
        SysUser sysUser = sysUserDAO.selectUserNamePhone(record.getUserName(), record.getPhone());
        if (sysUser != null) {
            throw new CustomizeExp("用户名或手机号已存在");
        }
        ActivationCode activationCode = userActivationMapper.selectActivationCode(code);
        if ("N".equals(activationCode.getStatus())) {
            throw new CustomizeExp("该邀请码已被使用");
        }
        record.setCreateTime(new Date());
        record.setCreateBy(record.getUserName());
        record.setUserId(IdUtil.simpleUUID());
        int c = sysUserDAO.insert(record);
        if (c==0){
            throw new CustomizeExp("用户注册失败");
        }
        ActivationCode activationCodeU = new ActivationCode();
        activationCodeU.setActivationBy(record.getUserName());
        activationCodeU.setActivationTime(new Date());
        activationCodeU.setStatus("N");
        activationCodeU.setId(code);
        userActivationMapper.updateActivationCode(activationCodeU);
        grantRole(record.getUserId(), roleIds);
        return c;
    }

    /**
     * 校验用户名是否存在
     * @param userName
     * @throws CustomizeExp
     */
    public void checkUserName(String userName) throws CustomizeExp {
        SysUser s = sysUserDAO.checkUserName(userName);
        if (s != null) {
            throw new CustomizeExp("该用户已存在");
        }
    }

    /**
     * 添加用户
     * @param record
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertSelective(SysUser record) {
        int code = sysUserDAO.insertSelective(record);
        return code;
    }

    /**
     * userId查询用户
     * @param userId
     * @return
     */
    @Override
    public SysUser selectByPrimaryKey(String userId) {
        SysUser sysUser = sysUserDAO.selectByPrimaryKey(userId);
        return sysUser;
    }

    /**
     * 用户名查询用户
     * @param userName
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUser selectUserName(String userName) {
        SysUser sysUser = sysUserDAO.selectUserName(userName);
        return sysUser;
    }

    /**
     * 更新用户
     * @param record 用户信息
     * @param roleIds 角色id
     * @return
     * @throws CustomizeExp
     */
    @CacheEvict(cacheNames = {"UserDetails","UserAuthorities"}, key = "#record.userId")
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKeySelective(SysUser record, List<String> roleIds) throws CustomizeExp {
        //更新用户信息
        if (!(ENABLE.equals(record.getStatus()) || PROHIBIT.equals(record.getStatus()) || record.getStatus() == null)) {
            throw new CustomizeExp("修改失败,用户状态只能Y/N");
        }
        int code = sysUserDAO.updateByPrimaryKeySelective(record);
        //没有传角色信息是不做用户角色修改
        if (roleIds == null || roleIds.size() == 0) {
            return code;
        }
        // 清空原有的角色, 赋予新角色.
        sysRoleDAO.deleteUserRoleByUserId(record.getUserId());
        sysRoleDAO.insertList(record.getUserId(), roleIds);
        return code;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(SysUser record) {
        int code = sysUserDAO.updateByPrimaryKey(record);
        return code;
    }

    /**
     * 用户赋予角色
     *
     * @param userId
     * @param roleIds
     */
    @CacheEvict(cacheNames = {"UserDetails","UserAuthorities"}, key = "#userId")
    @Transactional(rollbackFor = Exception.class)
    public void grantRole(String userId, List<String> roleIds) throws CustomizeExp {
        if (roleIds == null || roleIds.size() == 0) {
            throw new CustomizeExp("赋予的角色数组不能为空.");
        }
        // 清空原有的角色, 赋予新角色.
        sysRoleDAO.deleteUserRoleByUserId(userId);
        sysRoleDAO.insertList(userId, roleIds);
    }


    @Override
    public List<SysRole> selectRole(String record) {
        return sysUserDAO.selectUserSysRole(record);
    }

    /**
     * 用户登入
     * @param username
     * @param password
     * @return
     */
    @Override
    public Map<String, Object> login(String username, String password) throws CustomizeExp {
        SysUser sysUser = selectUserName(username);
        if (sysUser == null) {
            throw new CustomizeExp("用户名不存在");
        }
        if (!password.equals(sysUser.getUserPassword())) {
            throw new CustomizeExp("用户名或密码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(sysUser.getUserId(), password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String token = jwtTokenUtil.generateToken(userDetails);
        List<String> userGrantedAuthority = getUserGrantedAuthorityId(sysUser.getUserId());
        Map<String, Object> tokenMap = new HashMap<>(4);
        tokenMap.put("userName",sysUser.getUserName());
        tokenMap.put("Authority", userGrantedAuthority);
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return tokenMap;
    }


    /**
     * 查询所有用户信息不分页
     * @return
     */
    @Override
    public List<SysUser> selectAllSysUser() {
        return sysUserDAO.selectAllUser();
    }

    /**
     * 根据id查询用户拥有角色
     * @param userId
     * @return
     */
    @Override
    public Integer[] selectUserRoleId(String userId) {
        Integer[] integers = sysUserDAO.selectUserRoleId(userId);
        return integers;
    }

    @Override
    public String updateToken(String token) throws CustomizeExp {
        return null;
    }


    /**
     *  分页查询 不含角色数据
     * @param current
     * @param size
     * @return
     * @throws CustomizeExp
     */
    @Override
    public List<SysUser> queryAll(Integer current, Integer size) throws CustomizeExp {
        return userMapper.selectUserList(new Page<SysUser>(current, size)).getRecords();
    }

//    /**
//     * SysUser分页查询
//     * @param current
//     * @param size
//     * @return List<SysUserInfoPaging>
//     */
//    @Override
//    public List<SysUserInfoPaging> queryUserData(Integer current, Integer size) {
//        List<SysUser> records = userMapper.selectUserList(new Page(current, size)).getRecords();
//        List<SysRolePaging> sysRoles = userMapper.selectUserData(records);
//        List<SysUserInfoPaging> sysUserData = new ArrayList<>(records.size());
//        for (SysUser user : records) {
//            Stream<SysRolePaging> stream = sysRoles.stream();
//            List<SysRolePaging> collect = stream.filter(s ->
//                    s.getUserId().equals(user.getUserId()))
//                    .collect(Collectors.toList());
//            sysUserData.add(new SysUserInfoPaging(user, collect));
//        }
//        return sysUserData;
//    }

    /**
     * SysUser分页查询
     * @param current
     * @param size
     * @return List<SysUserInfoPaging>
     */
    @Override
    public IPage<JSONObject> queryUserData(Integer current, Integer size) {
        IPage<JSONObject> sysUserIPage = userMapper.selectUserInfoList(new Page(current, size));
        return sysUserIPage;
    }


    /**
     * 统计 SysUser记录数
     * @return SysUser表记录数
     */
    @Override
    public long countSysUser() {
        return sysUserDAO.countSysUser();
    }

    /**
     * 通过userId 获取所有权限
     * @param userId
     * @return 权限List<String>
     */
    @Override
    public List<String> getUserGrantedAuthority(String userId) {
        List<String> strings = sysUserDAO.selectUserPermission(userId);
        return strings;
    }

    /**
     * 通过userId 获取所有权限的id
     * @param userId
     * @return 权限List<String>
     */
    @Override
    public List<String> getUserGrantedAuthorityId(String userId) {
        List<String> strings = sysUserDAO.selectUserPermissionId(userId);
        return strings;
    }

    /**
     * 查询userIds是否可用
     * @param userId
     * @return Y/可用,N/不可用
     */
    @Override
    public String queryCheckUserId(String userId) {
        SysUser sysUser = userMapper.selectById(userId);
        if (sysUser == null) {
            return "Y";
        }
        return "N";
    }

    /**
     * 校验手机号是否可用
     * @param phone
     * @return Y可用N不可用
     */
    @Override
    public String queryCheckPhone(String phone) {
        SysUser sysUser = sysUserDAO.selectPhone(phone);
        if (sysUser == null) {
            return "Y";
        }
        return "N";
    }

    /**
     * 更新用户
     * @param jsonObject
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer insertUser(JSONObject jsonObject) throws CustomizeExp {
        String phone = jsonObject.get("phone").toString();
        List<String> roleIds = new ArrayList<>(1);
        roleIds.add(jsonObject.get("roleId").toString());
        String userName = jsonObject.get("userName").toString();
        SysUser sysUser = new SysUser();
        sysUser.setStatus("Y");
        sysUser.setPhone(phone);
        sysUser.setUserName(userName);
        sysUser.setUpdateTime(new Date());
        SysUser user = sysUserDAO.selectPhone(phone);
        sysUser.setUserId(user.getUserId());
        sysUser.setUpdateBy(securityUtil.currentUserName());
        int i = sysUserDAO.updateByPhoneKeySelective(sysUser);
        grantRole(sysUser.getUserId(), roleIds);
        return i;
    }

    /**
     * 验证Token是否有效
     * @param token
     * @return Token有效返回
     * @throws CustomizeExp
     */
    @Override
    public Map<String,Object> checkToken(String token) throws CustomizeExp {
        if (!jwtTokenUtil.validateToken(token)) {
            throw new CustomizeExp("Token无效");
        }
        AdminDetails details = (AdminDetails)jwtTokenUtil.generateAdminDetails(token);
        Map<String, Object> map = new HashMap<>(2);
        map.put("sysUserName",details.getSysUserName());
        List<String> authoritiesList = new ArrayList<>();
        details.getAuthorities().forEach(x -> authoritiesList.add(x.getAuthority()));
        map.put("authorization", authoritiesList);
        return map;
    }

    /**
     * 刷新Token
     * @param token
     * @return 成功返回刷新的token
     * @throws CustomizeExp
     */
    @Override
    public String refreshToken(String token) throws CustomizeExp {
        if (!jwtTokenUtil.validateToken(token)) {
            throw new CustomizeExp("Token无效Token刷新失败");
        }
        return jwtTokenUtil.refreshToken(token);
    }

}
