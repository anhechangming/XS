package com.cyd.xs.config;



import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.io.File;

/**
 * Web配置（静态资源映射）
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private UploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置：访问路径 /upload/** 映射到本地存储根路径
        String localBasePath = uploadConfig.getLocalBasePath().replace("\\", "/"); // 统一路径分隔符
        registry.addResourceHandler("/upload/**") // 前端访问路径前缀
                .addResourceLocations("file:" + localBasePath); // 本地文件路径（必须加file:前缀）
    }
}