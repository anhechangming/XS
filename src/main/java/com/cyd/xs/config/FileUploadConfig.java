package com.cyd.xs.config;
//
//
//import lombok.Data;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 文件上传配置类（复用基础路径，新增文件专属配置）
// */
//@Configuration
//@Data
//public class FileUploadConfig {
//    // 复用图片上传的本地根路径
//    @Value("${upload.local.base-path}")
//    private String localBasePath;
//
//    // 复用图片上传的访问基础URL
//    @Value("${upload.access-base-url}")
//    private String accessBaseUrl;
//
//    // 支持的文件后缀（从配置文件读取）
//    @Value("${upload.file.supported-suffix}")
//    private String[] supportedSuffix;
//
//    // 单文件最大大小（字节）
//    @Value("${upload.file.max-size}")
//    private long maxFileSize;
//
//    // 文件存储子目录（与图片区分开，如：upload/file/1001/20250520/）
//    public static final String FILE_STORAGE_SUB_DIR = "file";
//
//    public String getLocalBasePath() {
//        return localBasePath;
//    }
//
//    public void setLocalBasePath(String localBasePath) {
//        this.localBasePath = localBasePath;
//    }
//
//    public String getAccessBaseUrl() {
//        return accessBaseUrl;
//    }
//
//    public void setAccessBaseUrl(String accessBaseUrl) {
//        this.accessBaseUrl = accessBaseUrl;
//    }
//
//    public String[] getSupportedSuffix() {
//        return supportedSuffix;
//    }
//
//    public void setSupportedSuffix(String[] supportedSuffix) {
//        this.supportedSuffix = supportedSuffix;
//    }
//
//    public long getMaxFileSize() {
//        return maxFileSize;
//    }
//
//    public void setMaxFileSize(long maxFileSize) {
//        this.maxFileSize = maxFileSize;
//    }
//}

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 文件上传配置类（复用基础路径，新增文件专属配置）
 * 兼容原有 Service 层调用，无需修改其他代码
 */
@Configuration
@Data // Lombok 自动生成 getter/setter，避免手动编写（也可保留你原来的手动 getter/setter）
public class FileUploadConfig {
    // 复用图片上传的本地根路径（直接注入，与原字段名一致）
    @Value("${upload.local.base-path}")
    private String localBasePath;

    // 复用图片上传的访问基础URL（与原字段名一致）
    @Value("${upload.access-base-url}")
    private String accessBaseUrl;

    // 支持的文件后缀（解决 @Value 注入数组问题：用逗号分隔配置，注入后转数组）
    @Value("${upload.file.supported-suffix:pdf,doc,docx,ppt,pptx}") // 冒号后是默认值，避免配置缺失报错
    private String supportedSuffixStr; // 临时接收逗号分隔的字符串

    // 单文件最大大小（字节，与原字段名一致）
    @Value("${upload.file.max-size}")
    private long maxFileSize;

    // 文件存储子目录（保持原有静态常量，Service 层直接使用）
    public static final String FILE_STORAGE_SUB_DIR = "file";

    // ========== 核心：兼容原有 Service 层的 getSupportedSuffix() 方法 ==========
    // 原 Service 层调用 getSupportedSuffix() 获取 String[]，这里重写该方法，将字符串转数组
    public String[] getSupportedSuffix() {
        if (StringUtils.hasText(supportedSuffixStr)) {
            // 分割字符串为数组（去除前后空格，避免配置中多写空格导致问题）
            return Arrays.stream(supportedSuffixStr.split(","))
                    .map(String::trim)
                    .toArray(String[]::new);
        }
        return new String[0]; // 无配置时返回空数组，避免空指针
    }

    // ========== 可选：如果需要 List<String> 类型，可新增方法（不影响原有逻辑） ==========
    public List<String> getSupportedSuffixList() {
        return Arrays.asList(getSupportedSuffix());
    }

    // ========== 保留原有 getter/setter（Lombok 的 @Data 已生成，也可手动保留） ==========
    // 注：如果保留手动 getter/setter，需删除 @Data 注解，以下是手动版本（二选一即可）

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

    public long getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(long maxFileSize) {
        this.maxFileSize = maxFileSize;
    }

    // supportedSuffixStr 的 getter/setter（内部使用，Service 层不调用）
    public String getSupportedSuffixStr() {
        return supportedSuffixStr;
    }

    public void setSupportedSuffixStr(String supportedSuffixStr) {
        this.supportedSuffixStr = supportedSuffixStr;
    }

}