package com.shop.module.ticketManager.controller;

import com.shop.common.CommonResult;
import com.shop.module.admin.webexceptionhandler.CustomizeExp;
import com.shop.module.ticketManager.entity.TicketInfo;
import com.shop.module.ticketManager.service.TicketInfoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;
@RequestMapping("/ticket")
@Controller
public class TicketInfoController {
    @Autowired
    private TicketInfoService ticketinfoService;

    @ResponseBody
    @ApiOperation(value = "新增卡劵", notes = "新增一张卡劵信息")
    @PostMapping("/insertTicket")
    public CommonResult insertTicket(@RequestBody TicketInfo ticketInfo) throws CustomizeExp {
        ticketinfoService.insert(ticketInfo);
        return CommonResult.success(ticketInfo);
    }
    
    @ResponseBody
    @ApiOperation(value = "更新卡劵", notes = "更新卡劵信息")
    @PostMapping("/updateTicket")
    public CommonResult updateTicket(@RequestBody TicketInfo ticketInfo) throws CustomizeExp {
        ticketinfoService.updateByPrimaryKey(ticketInfo);
        return CommonResult.success(ticketInfo);
    }


    @ResponseBody
    @ApiOperation(value = "前台app查询所有卡卷", notes = "前台app查询所有卡劵信息")
    @GetMapping("/selectAllTicket")
    public CommonResult<List<TicketInfo>> selectAllTicket() throws CustomizeExp {
        List<TicketInfo> ticketInfos = ticketinfoService.selectAllTicket();
        return CommonResult.success(ticketInfos);
    }
    
    @ResponseBody
    @ApiOperation(value = "根据卡劵id查询卡劵信息", notes = "卡劵id查询卡劵信息")
    @GetMapping("/selectByPrimaryKey")
    public CommonResult selectByPrimaryKey(String id) throws CustomizeExp {
        TicketInfo ticketInfos = ticketinfoService.selectByPrimaryKey(id);
        return CommonResult.success(ticketInfos);
    }

    
    @ResponseBody
    @ApiOperation(value = "卡劵信息分页", notes = "卡劵信息分页")
    @GetMapping("/ticketPageHelper")
    public CommonResult ticketPageHelper(Integer current, Integer size) throws CustomizeExp {
        Map<String, Object> map = ticketinfoService.ticketPageHelper(current, size);
        return CommonResult.success(map);
    }

    @GetMapping("/selectByCreateBy")
    @ApiOperation(value = "通过卡劵创建人和状态进行分页", notes = "根据卡劵发布人和状态查询")
    @ResponseBody
    public CommonResult selectByCreateBy(Integer current, Integer size, String createdBy, String ticketStatus) throws CustomizeExp {
        Map<String, Object> map = ticketinfoService.selectByCreateBy(current, size, createdBy, ticketStatus);
        return CommonResult.success(map);
    }

    @GetMapping("/updateTicketStatus")
    @ApiOperation(value = "更改启用，禁用", notes = "切换禁用和启用")
    @ResponseBody
    public CommonResult updateTicketStatus(String id, String ticketStatus) throws CustomizeExp {
        ticketinfoService.updateTicketStatus(id, ticketStatus);
        return CommonResult.success("更改成功");
    }

    @GetMapping("/deleteByStatus")
    @ApiOperation(value = "禁用删除", notes = "禁用卡劵id删除")
    @ResponseBody
    public CommonResult deleteByPrimaryAndStatus(String id) throws CustomizeExp {
        ticketinfoService.deleteByPrimaryKey(id);
        return CommonResult.success("删除成功");
    }
}
