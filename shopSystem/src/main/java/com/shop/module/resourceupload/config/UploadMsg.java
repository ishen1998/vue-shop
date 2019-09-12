package com.shop.module.resourceupload.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author zhoulanzhen
 * @date 2019/8/004 13:34
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadMsg {
    public int status;
    public String msg;
    public String path;
}
