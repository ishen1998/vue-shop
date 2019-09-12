package com.shop.module.activity.entity;

import lombok.Data;
import java.io.Serializable;

@Data
public class TypeAndDis implements Serializable {
    private String preferenceType;
    private String preferenceDiscount;
}
