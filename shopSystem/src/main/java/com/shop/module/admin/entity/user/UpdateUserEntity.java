package com.shop.module.admin.entity.user;

import com.shop.module.admin.componet.paramverifica.CustomCheck;
import lombok.Data;

import javax.validation.Valid;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/7/025 9:46
 */
@Data
public class UpdateUserEntity {

    @Valid
    private SysUser sysUser;
    @CustomCheck(message = "用户角色参数错误!请检查是否有null")
    private List<String> roleIds;


}
