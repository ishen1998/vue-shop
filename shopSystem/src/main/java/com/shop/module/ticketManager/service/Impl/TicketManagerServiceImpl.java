package com.shop.module.ticketManager.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.service.CustomerInfoService;
import com.shop.module.ticketManager.dao.TicketCustomerRDAO;
import com.shop.module.ticketManager.entity.TicketCustomerR;
import com.shop.module.ticketManager.entity.TicketInfo;
import com.shop.module.ticketManager.service.TicketInfoService;
import com.shop.module.ticketManager.service.TicketManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketManagerServiceImpl implements TicketManagerService {

    @Autowired
    private TicketCustomerRDAO ticketCustomerDao;

    @Autowired
    private TicketInfoService ticketInfoService;

    @Autowired
    private CustomerInfoService customerInfoService;

    /**
     * 客户领取卡劵
     * @param cLoginName，ticketId
     * @return ticketCustomerR
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TicketCustomerR customerGetTicket(String cLoginName, String ticketId) throws CustomizeExp{
        String id=ticketId;
        TicketInfo ticketInfo= ticketInfoService.selectByPrimaryKey(id);//通过卡劵id查询卡劵
        CustomerInfo customerInfo = customerInfoService.selectByLoginName(cLoginName);
        String cId = customerInfo.getcId();
        TicketCustomerR ticketCustomerR=new TicketCustomerR();
        insertCustomerTicketInfo(ticketCustomerR, customerInfo, ticketId);//设置客户劵信息
        TicketCustomerR ticketCuss = ticketCustomerDao.selectByCustomerTicketId(cId,ticketId);//查询所有客户卡劵信息
        if(ticketCuss!=null){
            throw new CustomizeExp("卡劵已领取");
        }
        if(ticketInfo.getTicketNum() == 0){
            throw new CustomizeExp("卡劵数量为0");
        }
        if(!ticketInfo.getTicketStatus().equals("Y")){
            throw new CustomizeExp("卡劵禁用或者卡劵失效");
        }
        insert(ticketCustomerR);
        ticketInfo.setTicketNum(ticketInfo.getTicketNum() - 1);
        ticketInfoService.updateByPrimaryKey(ticketInfo);
        return ticketCustomerR;
    }

    /**
     * 新增客户卡劵信息
     * @param ticketCustomerR
     * @return ticketCustomerR
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(TicketCustomerR ticketCustomerR) {
        return ticketCustomerDao.insert(ticketCustomerR);
    }

    /**
     * 删除客户卡劵信息
     * @param id
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteCustomerTicket(String id) throws CustomizeExp {
        TicketCustomerR ticketCustomerR = ticketCustomerDao.selectCusTicketById(id);
        if(null==ticketCustomerR){
            throw new CustomizeExp("没有该客户");
        }
        return ticketCustomerDao.deleteCustomerTicket(id);
    }

    /**
     * 通过客户手机号查询信息
     * @param mobile，current，size
     * @return  map
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map selectByCustomerMobile(String mobile,Integer current,Integer size) throws CustomizeExp {
        Map<String,Object> map=new HashMap<>(2);
        List<TicketCustomerR> ticketCustomers = ticketCustomerDao.customerTicketPage(new Page<TicketCustomerR>(current, size),mobile).getRecords();
        if (ticketCustomers.size()==0 || ticketCustomers==null){
             throw new CustomizeExp("该客户没有任何卡劵");
        }
        for(TicketCustomerR ticketCustomer:ticketCustomers){
            if(new Date().after(ticketCustomer.getTicketInfo().getEndTime())){
                ticketCustomer.setUseStatus("3");
            }
            String ticketCustomerId= ticketCustomer.getTicketCustomerId();
            ticketCustomerDao.updateTicCusStatus(ticketCustomerId,ticketCustomer.getUseStatus());
        }
        map.put("customerTicketPage", ticketCustomers);
        map.put("customerTicketCount",ticketCustomerDao.selectCountTicket(mobile));
        return map;
    }

    //设置客户卡劵信息
    public void insertCustomerTicketInfo(TicketCustomerR ticketCustomerR,CustomerInfo customerInfo,String ticketId){
        ticketCustomerR.setcLoginName(customerInfo.getcLoginName());//客户名
        ticketCustomerR.setcId(customerInfo.getcId());//客户id
        ticketCustomerR.setCreatedTime(new Date()); //激活时间
        ticketCustomerR.setCreatedBy(customerInfo.getcLoginName());//得到客户名
        ticketCustomerR.setMobile(customerInfo.getMobile()); //客户手机号
        ticketCustomerR.setUseStatus("1");    //使用状态
        ticketCustomerR.setTicketId(ticketId);  //票id
    }
}
