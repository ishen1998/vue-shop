package com.shop.module.customerInfo.controller;

import cn.hutool.core.date.DateTime;
import com.shop.common.CommonResult;
import com.shop.module.customerInfo.entity.AnalyticalDate;
import com.shop.module.customerInfo.service.CustomerAnalyticalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/9/002 8:47
 */
@RequestMapping("/customerAnalysis")
@RestController
public class CustomerAnalysisController {

    @Autowired
    private CustomerAnalyticalService analyticalService;

    @GetMapping("/analyticaldata")
    public CommonResult analyticaldata(long beginTime,long endTime) {
        Map<String, Object> analyticaldata = analyticalService.analyticaldata(beginTime,endTime);
        return CommonResult.success(analyticaldata);
    }

    @GetMapping("/data")
    public CommonResult getData(long beginTime,long endTime) {
        return CommonResult.success(analyticalService.getData(beginTime,endTime));
    }
}
