package com.shop.module.addressManager.service.Impl;

import com.shop.module.addressManager.dao.CustomerAddressRDAO;
import com.shop.module.addressManager.entity.CustomerAddressR;
import com.shop.module.addressManager.service.CustomerAddressRService;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerAddressRServiceImpl implements CustomerAddressRService {
    @Autowired
    private CustomerAddressRDAO customerAddressDao;

    /**
     * 新增地址信息
     * @Param address
     * @return
     * @throws CustomizeExp
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insert(CustomerAddressR address) throws CustomizeExp {
        if (address.getcId().isEmpty() || address.getConsigneeName().isEmpty() || address.getConsigneeMobile().isEmpty() || address.getConsigineeAddress().isEmpty()) {
            throw new CustomizeExp("输入信息的信息不合法");
        }
        updateAddressStatus(address);
        return customerAddressDao.insert(address);
    }

    /**
     * 删除地址信息
     * @Param id
     * @return
     * @throws CustomizeExp
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int deleteByPrimaryKey(String id) throws CustomizeExp {
        if (id == null || id.isEmpty()) {
            throw new CustomizeExp("地址id不存在");
        }
        return customerAddressDao.deleteByPrimaryKey(id);
    }

    /**
     * 根据地址id查询地址信息
     * @Param id
     * @return  customerAddress
     * @throws CustomizeExp
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CustomerAddressR selectByPrimaryKey(String id) throws CustomizeExp {
        CustomerAddressR customerAddress =customerAddressDao.selectByPrimaryKey(id);
        if(customerAddress==null){
            throw new CustomizeExp("无任何地址信息");
        }
        return customerAddress;
    }

    /**
     * 更新地址信息
     * @Param address
     * @return  i
     * @throws CustomizeExp
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateByPrimaryKey(CustomerAddressR address) throws CustomizeExp {
        updateAddressStatus(address);
        int i = customerAddressDao.updateByPrimaryKey(address);
        if(i<=0){
            throw new CustomizeExp("更新异常");
        }
        return i;
    }

    /**
     * 查询所有的客户地址信息
     * @Param cId
     * @return  customerAddressList
     * @throws CustomizeExp
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<CustomerAddressR> selectByCustomerId(String cId) throws CustomizeExp {
        List<CustomerAddressR> customerAddressList =customerAddressDao.selectByCustomerId(cId);
        if(customerAddressList==null || customerAddressList.size()==0){
            throw new CustomizeExp("没有任何客户地址信息");
        }
        return customerAddressList;
    }

    /**
     * 根据客户id更改默认状态
     * @Param cId
     * @return
     * @throws CustomizeExp
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int updateStatusByAddId(String customerAddressId, String addressStatus) {
        return customerAddressDao.updateStatusByAddId(customerAddressId, addressStatus);
    }

    //判断前台传过来的默认地址状态进行更新
    public void updateAddressStatus(CustomerAddressR address) {
        if (address.getAddressStatus().equals("Y")) {
            List<CustomerAddressR> customerAdds = customerAddressDao.selectByCustomerId(address.getcId());
            for (CustomerAddressR address1 : customerAdds) {
                address1.setAddressStatus("N");
                updateStatusByAddId(address1.getCustomerAddressId(), address1.getAddressStatus());
            }
        }
    }
}