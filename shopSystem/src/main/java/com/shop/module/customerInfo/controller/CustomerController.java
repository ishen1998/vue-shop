package com.shop.module.customerInfo.controller;

import com.shop.common.CommonResult;
import com.shop.componet.JwtTokenUtil;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.customerInfo.entity.CustomerInfo;
import com.shop.module.customerInfo.entity.CustomerInfoTicket;
import com.shop.module.customerInfo.service.CustomerInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CustomerController {
    @Autowired
    private CustomerInfoService customerService;

    @ResponseBody
    @ApiOperation(value = "注册客户信息", notes = "注册客户")
    @RequestMapping("/register")
    public CommonResult register(@RequestBody CustomerInfo customer) throws CustomizeExp {
        CommonResult result = insert(customer);
        return result;
    }

    @ResponseBody
    @RequestMapping("/insert")
    @ApiOperation(value = "添加客户信息", notes = "添加客户")
    public CommonResult insert(@RequestBody CustomerInfo customer) throws CustomizeExp {
        customerService.insert(customer);
        return CommonResult.success(customer);
    }

    @ResponseBody
    @ApiOperation(value = "通过客户id删除客户", notes = "通过客户id删除客户")
    @RequestMapping("/deleteById")
    public CommonResult deleteByPrimaryKey(String id) throws CustomizeExp {
        customerService.deleteByPrimaryKey(id);
        return CommonResult.success("删除成功");
    }

    @ResponseBody
    @ApiOperation(value = "通过客户手机号查询客户信息", notes = "客户手机号查询客户信息")
    @RequestMapping("/selectByMobile")
    public CommonResult selectByMobile(String mobile) throws CustomizeExp {
        CustomerInfoTicket customerInfoTicket = customerService.selectByMobile(mobile);
        return CommonResult.success(customerInfoTicket,"手机号码查询成功");
    }

    @ResponseBody
    @ApiOperation(value = "通过客户Id查询客户信息", notes = "客户Id查询客户信息")
    @RequestMapping("/selectByPrimaryKey")
    public CommonResult selectByPrimaryKey(String id) throws CustomizeExp {
        CustomerInfo customerInfo = customerService.selectByPrimaryKey(id);
        return CommonResult.success(customerInfo);
    }

    @ResponseBody
    @ApiOperation(value = "通过客户Id修改客户信息", notes = "客户Id修改客户信息")
    @RequestMapping("/updateByPrimaryKey")
    public CommonResult updateByPrimaryKey(@RequestBody CustomerInfo customer) throws CustomizeExp {
        customerService.updateByPrimaryKey(customer);
        return CommonResult.success(customer);
    }


    @ResponseBody
    @ApiOperation(value = "客户信息分页", notes = "客户信息分页")
    @RequestMapping("/pageHolder")
    public CommonResult pageHolder(Integer current, Integer size) throws CustomizeExp {
        Map map = customerService.pageHolder(current, size);
        return CommonResult.success(map);
    }

    @PostMapping("/testlogin")
    @ApiOperation(value = "客户登录", notes = "客户登录")
    @ResponseBody
    public CommonResult userLogin(@RequestBody CustomerInfo customerInfo) throws CustomizeExp {
        Map s = customerService.loginToken(customerInfo);
        return CommonResult.success(s);
    }

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/reToken")
    @ApiOperation(value = "刷新token", notes = "刷新token")
    @ResponseBody
    public CommonResult reToken(String token) throws CustomizeExp {
        if (!jwtTokenUtil.validateToken(token)) {
            throw new CustomizeExp("token已失效");
        }
        String s = jwtTokenUtil.refreshToken(token);
        return CommonResult.success("Bearer " + s);
    }

    @GetMapping("/checkToken/{token}")
    @ApiOperation(value = "检查token", notes = "检查token")
    @ResponseBody
    public CommonResult checkToken(@PathVariable String token) throws CustomizeExp {
        Map map = customerService.checkToken(token);
        return CommonResult.success(map);
    }

    @GetMapping("/Customer/status")
    @ApiOperation(value = "禁用状态", notes = "禁用状态")
    @ResponseBody
    public CommonResult updateCustomersStatus(String cId,String status) throws CustomizeExp {
        return CommonResult.success(customerService.enableCustomers(cId,status));
    }


}
