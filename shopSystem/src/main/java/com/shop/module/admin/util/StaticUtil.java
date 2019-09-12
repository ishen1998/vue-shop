package com.shop.module.admin.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhoulanzhen
 * @date 2019/8/022 9:35
 */
@Data
public  class StaticUtil<T> {
    public static final String STATUS = "Y";

//    public static List<List<T>> splitList(List<T> list, int len) {
//        if (list == null || list.size() == 0 || len < 1) {
//            return null;
//        }
//        List<List<T>> result = new ArrayList<List<T>>();
//        int size = list.size();
//        int count = (size + len - 1) / len;
//        for (int i = 0; i < count; i++) {
//            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
//            result.add(subList);
//        }
//        return result;
//    }
}
