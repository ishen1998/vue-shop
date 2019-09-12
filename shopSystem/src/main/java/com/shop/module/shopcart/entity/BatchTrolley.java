package com.shop.module.shopcart.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/002 9:21
 */
@Getter
@Setter
public class BatchTrolley {
    @NotBlank(message = "客户ID不能为空")
    private String cId;
    private List<String> itemId;
}
