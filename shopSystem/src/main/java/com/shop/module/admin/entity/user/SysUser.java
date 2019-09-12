package com.shop.module.admin.entity.user;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * sys_user
 *
 * @author
 */
/**
 * null值不做序列化操作
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@TableName("sys_user")
public class SysUser implements Serializable {
    /**
     * 主键
     */
    @TableId
    private String userId;

    /**
     *
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "用密码不能为空")
    private String userPassword;

    private String phone;

    /**
     * 用户状态
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人
     */
    private String updateBy;


}