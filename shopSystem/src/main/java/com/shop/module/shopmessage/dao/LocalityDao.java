package com.shop.module.shopmessage.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocalityDao {
    List<String> selectLocality();
}
