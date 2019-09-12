package com.shop.common;

import com.shop.module.activity.entity.ActivityItem;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author zhoulanzhen
 * @date 2019/7/016 14:23
 */
@Data
public class CommonResult<T> {
    private long code;
    private String message;
    private T data;

    public CommonResult() {
    }

    protected CommonResult(long code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回结果
     *
     * @param data
     */
    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<>(200, "成功", data);
    }

    /**
     * 成功返回结果
     *
     * @param data
     */
    public static <T> CommonResult<T> success(T data,String message) {
        return new CommonResult<>(200, message, data);
    }

    /**
     * 失败返回结果
     *
     * @param data
     */
    public static <T> CommonResult<T> failed(String message, T data) {
        return new CommonResult<>(500, message, data);
    }

    /**
     * 业务失败返回结果
     *
     * @param data
     */
    public static <T> CommonResult<T> failed(long code,String message, T data) {
        return new CommonResult<>(code, message, data);
    }

    /**
     * 参数校验失败返回结果
     *
     * @param data
     */
    public static <T> CommonResult<T> paramFailed(String message, T data) {
        return new CommonResult<>(400, message, data);
    }

    public static <T> List<List<T>> splitList(List<T> list, int len) {
        if (list == null || list.size() == 0 || len < 1) {
            return null;
        }
        List<List<T>> result = new ArrayList<List<T>>();
        int size = list.size();
        int count = (size + len - 1) / len;
        for (int i = 0; i < count; i++) {
            List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
            result.add(subList);
        }
        return result;
    }
}
