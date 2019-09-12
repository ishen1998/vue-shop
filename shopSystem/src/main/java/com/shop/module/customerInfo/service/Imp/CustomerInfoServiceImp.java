package com.shop.module.customerInfo.service.Imp;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.common.CommonResult;
import com.shop.componet.JwtTokenUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.dao.CustomerInfoDao;
import com.shop.module.customerInfo.dao.CustomerPageDao;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.entity.CustomerInfoTicket;
import com.shop.module.customerInfo.service.CustomerInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CustomerInfoServiceImp implements CustomerInfoService {

    private static final String status = "N";

    @Autowired
    private CustomerInfoDao customerDao;

    @Autowired
    private CustomerPageDao customerPageDao;
    
    @Value("${jwt.expiration}")
    private long exp;

    /**
     * 通过客户id删除客户
     * @param id
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String id) throws CustomizeExp {
        CustomerInfo customerInfo = selectByPrimaryKey(id);
        if (customerInfo == null) {
            throw new CustomizeExp("删除失败");
        }
        //删除缓存中的数据
        redisTemplate.delete("Customer:" + id);
        return customerDao.deleteByPrimaryKey(id);
    }

    /**
     * 通过客户手机号查询
     * @param mobile
     * @return customer
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerInfoTicket selectByMobile(String mobile) throws CustomizeExp {
        CustomerInfoTicket customer = customerPageDao.selectByMobile(mobile);
        if (customer == null) {
            throw new CustomizeExp("此客户不存在");
        }
        return customer;
    }

    /**
     * 添加客户信息
     * @param customer
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(CustomerInfo customer) throws CustomizeExp {
        CustomerInfo customers1 = customerDao.selectByMobileOrName(customer.getMobile(),null);
        CustomerInfo customers2 = customerDao.selectByMobileOrName(null,customer.getcLoginName());
        if(customers1!=null && customers2!=null){
            throw new CustomizeExp("用户名和手机号都存在");
        }
        if(customers1!=null){
            throw new CustomizeExp("手机号已存在");
        }
        if(customers2!=null){
            throw new CustomizeExp("用户名已存在");
        }

        return customerDao.insert(customer);
    }

    /**
     * 客户分页
     * @param current,size
     * @return  map
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    public Map pageHolder(Integer current, Integer size) throws CustomizeExp {
        Map<String, Object> map = new HashMap<>(2);
        List<JSONObject> records1 = customerPageDao.selectUserTicketList(new Page<CustomerInfo>(current, size)).getRecords();
        if(records1==null || records1.size()==0){
            throw  new CustomizeExp("没有此客户");
        }
        map.put("customerInfoList", records1);
        map.put("count", customerPageDao.countUser());
        return map;
    }

    /**
     * 通过客户id查询客户信息
     * @param id
     * @return  customer
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerInfo selectByPrimaryKey(String id) throws CustomizeExp {
        CustomerInfo customer = customerDao.selectByPrimaryKey(id);
        if (null==customer) {
            throw new CustomizeExp("没有该客户");
        }
        return customer;
    }

    /**
     * 通过客户id更改客户信息
     * @param customer
     * @return  customer
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerInfo updateByPrimaryKey(CustomerInfo customer) throws CustomizeExp {
        CustomerInfo customerInfo = customerDao.selectByPrimaryKey(customer.getcId());
        String mobile = customerInfo.getMobile();
        if (customer.getSex() == null || customer.getImg() == null ||
                customer.getEmail() == null || customer.getNickName() == null) {
            customer.setNickName(customerInfo.getNickName());
            customer.setImg(customerInfo.getImg());
            customer.setSex(customerInfo.getSex());
            customer.setEmail(customerInfo.getEmail());
        }
        customer.setcLoginName(customerInfo.getcLoginName());
        CustomerInfo customers1 = selectByMobileOrName(customer.getMobile(), null);
        if (customers1 != null && !customers1.getMobile().equals(mobile)) {
            throw new CustomizeExp("手机号已存在,无法更改");
        }
        customerDao.updateByPrimaryKey(customer);
        //删除缓存中的数据
        //redisTemplate.delete("Customer:" + customerInfo.getcId());
        return customer;
    }

    /**
     * 查询账户名及绑定手机号情况
     * @param mobile，cLoginName
     * @return  customer
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerInfo selectByMobileOrName(String mobile,String cLoginName) {
        return customerDao.selectByMobileOrName(mobile,cLoginName);
    }

    /**
     * 根据客户名查询客户信息
     * @param cLoginName
     * @return  customer
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerInfo selectByLoginName(String cLoginName) {
        return customerDao.selectByLoginName(cLoginName);
    }


    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    /**
     * 客户登录
     * @param customer
     * @return map
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map loginToken(CustomerInfo customer) throws CustomizeExp {
        CustomerInfo customerinfo = selectByLoginName(customer.getcLoginName());
        Map<String,Object>  map=new HashMap<>();
        if (customerinfo == null) {
            throw new CustomizeExp("账户不存在");
        }
        if (!customer.getPassword().equals(customerinfo.getPassword())) {
            throw new CustomizeExp("账户或密码错误");
        }
        if (status.equals(customerinfo.getStatus())) {
            throw new CustomizeExp("账户已被禁用");
        }
        map.put("customer", customerinfo);
        String token = jwtTokenUtil.customergenerateToken(customerinfo);
        map.put("token", token);
        redisTemplate.opsForValue().set("Customer:"+customerinfo.getcId(),token);
        redisTemplate.expire("Customer:"+customerinfo.getcId(), exp, TimeUnit.SECONDS);
        return map;
    }

   /**
    * 检查token
    * */
    @Override
    public Map checkToken(String token) throws CustomizeExp {
        if (!jwtTokenUtil.validateToken(token)) {
            throw new CustomizeExp("token已失效");
        }
        Claims claimsFromToken = jwtTokenUtil.getClaimsFromToken(token);
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("phone", claimsFromToken.get("phone").toString());
        map.put("cLoginName", claimsFromToken.get("UserId").toString());
        map.put("exp", claimsFromToken.getExpiration());
        return map;
    }

    /**
     * 禁用客户
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int enableCustomers(String cId, String status) throws CustomizeExp {
        if (cId == null || cId.isEmpty()) {
            throw new CustomizeExp("启用客户id不能为空");
        }
        if (status == null || status.isEmpty()) {
            throw new CustomizeExp("状态不能为空");
        }
        if (!("Y".equals(status)||"N".equals(status))){
            throw new CustomizeExp("状态只能为Y/N");
        }
        redisTemplate.delete("Customer:"+cId);
        return customerDao.updateCustomerStatus(cId, status);
    }


}
