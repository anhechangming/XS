package com.cyd.xs.Utils;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.cyd.xs.config.FileUploadConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 文件上传工具类（支持PDF/Word/PPT）
 */
@Component
public class FileUploadUtil {
    @Resource
    private FileUploadConfig fileUploadConfig;

    /**
     * 上传文件到本地
     * @param file 待上传文件
     * @param userId 用户ID（分目录用）
     * @return 上传结果封装（URL、原始文件名、格式化大小）
     */
    public FileUploadResult uploadFileToLocal(MultipartFile file, Long userId) throws IOException {
        // 1. 校验文件合法性（非空、格式、大小）
        validateFile(file);

        // 2. 获取文件基础信息
        String originalFileName = file.getOriginalFilename(); // 原始文件名：简历模板（运营岗）.pdf
        String suffix = FileUtil.extName(originalFileName).toLowerCase(); // 文件后缀：pdf
        long fileSize = file.getSize(); // 文件大小（字节）

        // 3. 生成存储路径（根路径/文件子目录/用户ID/日期/）
        String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
        // 完整相对路径：file/1001/20250520（与图片的image目录区分）
        String relativeDir = FileUploadConfig.FILE_STORAGE_SUB_DIR + File.separator + userId + File.separator + dateDir;
        // 绝对路径：D:/jobhub/upload/file/1001/20250520
        String absoluteDir = fileUploadConfig.getLocalBasePath() + relativeDir;

        // 4. 创建目录（不存在则递归创建）
        File dir = new File(absoluteDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 5. 生成随机文件名（避免重复，格式：UUID.后缀）
        String randomFileName = UUID.randomUUID().toString() + "." + suffix;

        // 6. 保存文件到本地
        File targetFile = new File(absoluteDir + File.separator + randomFileName);
        file.transferTo(targetFile);

        // 7. 生成可访问URL（访问基础URL + 相对路径 + 随机文件名）
        String relativePath = relativeDir + File.separator + randomFileName;
        String fileUrl = fileUploadConfig.getAccessBaseUrl() + relativePath.replace("\\", "/"); // 统一URL分隔符

        // 8. 封装结果（含格式化大小）
        FileUploadResult result = new FileUploadResult();
        result.setFileUrl(fileUrl);
        result.setFileName(originalFileName);
        result.setFileSize(formatFileSize(fileSize)); // 格式化大小：2.5MB

        return result;
    }

    /**
     * 校验文件合法性（非空、格式、大小）
     */
    private void validateFile(MultipartFile file) {
        // 1. 校验文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传文件不能为空");
        }

        // 2. 校验文件大小
        if (file.getSize() > fileUploadConfig.getMaxFileSize()) {
            throw new IllegalArgumentException("文件大小不能超过20MB");
        }

        // 3. 校验文件格式（通过后缀）
        String originalFileName = file.getOriginalFilename();
        String suffix = FileUtil.extName(originalFileName).toLowerCase();
        boolean isSupported = false;
        for (String supportedSuffix : fileUploadConfig.getSupportedSuffix()) {
            if (supportedSuffix.equals(suffix)) {
                isSupported = true;
                break;
            }
        }
        if (!isSupported) {
            throw new IllegalArgumentException("仅支持PDF/Word/PPT格式（后缀：pdf、doc、docx、ppt、pptx）");
        }
    }

    /**
     * 格式化文件大小（字节 → KB/MB）
     * 示例：2048 → 2KB，2621440 → 2.5MB
     */
    private String formatFileSize(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + "B";
        } else if (fileSize < 1024 * 1024) {
            return String.format("%.1fKB", fileSize / 1024.0);
        } else {
            return String.format("%.1fMB", fileSize / (1024.0 * 1024.0));
        }
    }

    /**
     * 文件上传结果封装类（内部工具类）
     */
    public static class FileUploadResult {
        private String fileUrl;    // 文件访问URL
        private String fileName;   // 原始文件名
        private String fileSize;   // 格式化文件大小

        // getter/setter
        public String getFileUrl() { return fileUrl; }
        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
        public String getFileName() { return fileName; }
        public void setFileName(String fileName) { this.fileName = fileName; }
        public String getFileSize() { return fileSize; }
        public void setFileSize(String fileSize) { this.fileSize = fileSize; }
    }
}