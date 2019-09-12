package com.shop.module.activity.contrloler;

import com.shop.common.CommonResult;
import com.shop.module.activity.service.PreferenceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/activity")
@RestController
public class PreferenceInfoControl {
    @Autowired
    private PreferenceInfoService preferenceInfoService;

    /**
     * 返回优惠信息
     * @return
     */
    @RequestMapping("/selectPreferenceInfo")
    public CommonResult selectPreferenceInfo() {
        return preferenceInfoService.selectInfo();
    }
}
