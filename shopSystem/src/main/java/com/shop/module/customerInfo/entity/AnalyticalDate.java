package com.shop.module.customerInfo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author zhoulanzhen
 * @date 2019/9/002 9:56
 */
@Getter
@Setter
public class AnalyticalDate {

    private String customerDate;
    private int customerCount;
    private int orderCount;
}
