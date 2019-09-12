package com.shop.module.addressManager.controller;

import com.shop.common.CommonResult;
import com.shop.module.addressManager.entity.CustomerAddressR;
import com.shop.module.addressManager.service.CustomerAddressRService;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AddressManagerController {
    @Autowired
    private CustomerAddressRService addressService;

    @ApiOperation(value = "新增地址", notes = "新增客户地址")
    @ResponseBody
    @PostMapping("/insertAddress")
    public CommonResult insertAddress(@RequestBody CustomerAddressR address) throws CustomizeExp {
        addressService.insert(address);
        return CommonResult.success(address);
    }

    @ApiOperation(value = "更新客户地址", notes = "更新客户地址")
    @ResponseBody
    @PostMapping("/updateAddress")
    public CommonResult updateByCustomerAddressId(@RequestBody CustomerAddressR address) throws CustomizeExp {
        addressService.updateByPrimaryKey(address);
        return CommonResult.success(address);
    }
   
    @ResponseBody
    @ApiOperation(value = "根据客户地址id删除收货地址", notes = "根据客户地址id删除收货地址")
    @GetMapping("/deleteByAddressId")
    public CommonResult deleteByAddressId(String id) throws CustomizeExp {
        int i = addressService.deleteByPrimaryKey(id);
        return CommonResult.success(i);
    }

    @ApiOperation(value = "根据客户id查询所有地址", notes = "根据客户id查询所有地址")
    @ResponseBody
    @GetMapping("/selectByCustomerId")
    public CommonResult selectByCustomerId(String cId) throws CustomizeExp {
        List<CustomerAddressR> customerAddressList =addressService.selectByCustomerId(cId);
        return CommonResult.success(customerAddressList);
    }

    /**
     * 根据地址id查询收货地址信息
     * */
    @ApiOperation(value = "根据地址id查询收货地址信息", notes = "根据地址id查询收货地址信息")
    @ResponseBody
    @GetMapping("/selectByAddressId")
    public CommonResult selectByPrimaryKey(String id) throws CustomizeExp {
        CustomerAddressR customerAddress =addressService.selectByPrimaryKey(id);
        return CommonResult.success(customerAddress);
    }
}
