package com.shop.module.resourceupload;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author zhoulanzhen
 * @date 2019/8/008 11:34
 */
@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
        private String uploadDir;

        public String getUploadDir() {
            return uploadDir;
        }

        public void setUploadDir(String uploadDir) {
            this.uploadDir = uploadDir;
        }
}
