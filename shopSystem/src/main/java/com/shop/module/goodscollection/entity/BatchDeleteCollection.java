package com.shop.module.goodscollection.entity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author YeLei
 */
@Getter
@Setter
public class BatchDeleteCollection {
    @NotBlank(message = "客户ID不能为空")
    private String cId;

    private List<String> id;
}
