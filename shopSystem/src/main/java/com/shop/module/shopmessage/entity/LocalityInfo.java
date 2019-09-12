package com.shop.module.shopmessage.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class LocalityInfo implements Serializable {
    private String localityId;
    private String locality;
    private String locality_img;
    private String by;
}
