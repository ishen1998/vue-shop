package com.shop.module.admin.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author zhoulanzhen
 * @date 2019/8/030 16:53
 */
@Data
@TableName("activation_code")
public class ActivationCode {

    @TableId
    private String id;
    private String activationBy;
    private Date activationTime;
    private String status;
}
