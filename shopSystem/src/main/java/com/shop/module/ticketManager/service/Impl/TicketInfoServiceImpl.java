package com.shop.module.ticketManager.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.ticketManager.dao.TicketInfoDAO;
import com.shop.module.ticketManager.entity.TicketInfo;
import com.shop.module.ticketManager.service.TicketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TicketInfoServiceImpl implements TicketInfoService {
    @Autowired
    private TicketInfoDAO ticketDao;

    /**
     * 根据卡劵id删除卡劵
     * @param id
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String id) throws CustomizeExp {
        TicketInfo ticketInfo =selectByPrimaryKey(id);
        if (ticketInfo.getTicketStatus().equals("Y")) throw new CustomizeExp("该卡劵为启用状态,请先禁用");
        return ticketDao.deleteByPrimaryKey(id);
    }


    /**
     * 新增卡劵
     * @param ticketInfo
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(TicketInfo ticketInfo) throws CustomizeExp {
        if(new Date().after(ticketInfo.getEndTime())){
            throw new CustomizeExp("无法添加过期卡劵");
        }
        if(ticketInfo.getTicketNum()==0){
            ticketInfo.setTicketStatus("J");
        }
        return ticketDao.insert(ticketInfo);
    }

    /**
     * 通过卡劵id更改禁用、启用状态
     * @param id，ticketStatus
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateTicketStatus(String id, String ticketStatus) throws CustomizeExp {
        TicketInfo ticketInfo = ticketDao.selectByPrimaryKey(id);
        String ticketStatus1 = "";
        if (ticketInfo.getTicketStatus().equals("Y") || ticketInfo.getTicketStatus().equals("J")) {
            ticketStatus1 = ticketStatus;
            ticketDao.updateOnlyTicketStatus(id, ticketStatus1);
        }
        if (ticketInfo.getTicketStatus().equals("N")) throw new CustomizeExp("该卡劵已过期");
    }

    /**
     * 查询所有卡劵
     * @return ticketInfos
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<TicketInfo> selectAllTicket( ) throws CustomizeExp {
        List<TicketInfo> ticketInfos = ticketDao.selectAllTicket();
        if(ticketInfos==null || ticketInfos.size()==0){
            throw new CustomizeExp("无任何卡劵信息");
        }
        for(TicketInfo ticket:ticketInfos){
           if(ticket.getTicketNum()==0){
               ticket.setTicketStatus("J");
           }
           if(new Date().after(ticket.getEndTime())){
                ticket.setTicketStatus("N");
            }
           ticketDao.updateOnlyTicketStatus(ticket.getTicketId(), ticket.getTicketStatus());
        }
        return ticketInfos;
    }

    /**
     * 根据卡劵id查询卡劵信息
     * @param id
     * @return ticketInfo
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public TicketInfo selectByPrimaryKey(String id) throws CustomizeExp {
        TicketInfo ticketInfo = ticketDao.selectByPrimaryKey(id);
         if(ticketInfo==null){
             throw  new CustomizeExp("该卡劵id不存在");
         }
        return ticketInfo;
    }


    /**
     * 根据创建人和卡劵状态进行查询分页
     * @param current，size，createdBy，ticketStatus
     * @return map
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map selectByCreateBy(Integer current, Integer size, String createdBy, String ticketStatus) throws CustomizeExp {
        Map<String, Object> map = new HashMap<>();
        List<TicketInfo> records = ticketDao.selectTicketCreateBy(new Page<TicketInfo>(current, size), createdBy, ticketStatus).getRecords();
        Integer ticketCount = ticketDao.selectTicketCreateByCount(createdBy, ticketStatus);
        if(createdBy.isEmpty() && ticketStatus.isEmpty()){
            throw new CustomizeExp("该条件不满足查询");
        }
        map.put("ticketRecords", records);
        map.put("ticketCount", ticketCount);
        return map;
    }

    /**
     * 查询所有卡劵以及分页
     * @param current，size
     * @return map
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map ticketPageHelper(Integer current, Integer size) throws CustomizeExp{
        Map<String, Object> map = new HashMap<>(2);
        List<TicketInfo> tickets = ticketDao.ticketPageHelper(new Page<TicketInfo>(current, size)).getRecords();
        map.put("ticketInfo", tickets);
        map.put("count", ticketDao.countTicket());
        if (null == tickets || tickets.size() == 0) {
            throw new CustomizeExp("无任何卡劵");
        }
        return map;
    }

    /**
     * 查询所有卡劵以及分页
     * @param ticketInfo
     * @return
     * @throws CustomizeExp
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(TicketInfo ticketInfo) throws CustomizeExp {
        TicketInfo oldTicketInfo =selectByPrimaryKey(ticketInfo.getTicketId());
        if (ticketInfo.getTicketTitle() == null || ticketInfo.getTicketRule() == null
                || ticketInfo.getPreferentialFee() == null || ticketInfo.getTicketNum() == null
                || ticketInfo.getStartTime() == null || ticketInfo.getEndTime() == null) {
            ticketInfo.setTicketTitle(oldTicketInfo.getTicketTitle());//卡劵标题
            ticketInfo.setPreferentialFee(oldTicketInfo.getPreferentialFee());//卡劵面值
            ticketInfo.setTicketRule(oldTicketInfo.getTicketRule());//卡劵规则
            ticketInfo.setStartTime(oldTicketInfo.getStartTime());//卡劵开始时间
            ticketInfo.setEndTime(oldTicketInfo.getEndTime());//卡劵结束时间
            ticketInfo.setTicketNum(oldTicketInfo.getTicketNum());//卡劵数量
        }
        if(new Date().after(ticketInfo.getEndTime())){
            throw new CustomizeExp("无法更新过期卡劵");
        }
        if(ticketInfo.getTicketNum()==0){
            ticketInfo.setTicketStatus("J");
        }
        return ticketDao.updateByPrimaryKey(ticketInfo);
    }

}
