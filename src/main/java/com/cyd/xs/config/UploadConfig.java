package com.cyd.xs.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 上传配置类
 */
@Configuration
@Data
public class UploadConfig {
    // 本地存储根路径
    @Value("${upload.local.base-path}")
    private String localBasePath;

    // 图片访问基础URL
    @Value("${upload.access-base-url}")
    private String accessBaseUrl;

    // 支持的图片后缀
    public static final String[] SUPPORTED_IMAGE_SUFFIX = {"jpg", "jpeg", "png"};

    // 单文件最大大小（5MB）
    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    // 单次最多上传文件数
    public static final int MAX_FILE_COUNT = 4;

    public String getLocalBasePath() {
        return localBasePath;
    }

    public void setLocalBasePath(String localBasePath) {
        this.localBasePath = localBasePath;
    }

    public String getAccessBaseUrl() {
        return accessBaseUrl;
    }

    public void setAccessBaseUrl(String accessBaseUrl) {
        this.accessBaseUrl = accessBaseUrl;
    }
}