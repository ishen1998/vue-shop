package com.shop.module.admin.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author zhoulanzhen
 * @date 2019/7/025 15:54
 */
@Getter
@Setter
public class SysPermission {
    private Integer id;
    private Integer pid;
    private String name;
    private String value;
    private String icon;
    private String type;
    private String uri;
    private String status;
    private Date createTime;
}
