package com.cyd.xs.Utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.cyd.xs.config.UploadConfig;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 上传工具类
 */
@Component
public class UploadUtil {
    @Resource
    private UploadConfig uploadConfig;

    /**
     * 上传图片到本地
     * @param file 图片文件
     * @param userId 用户ID（用于分目录）
     * @return 图片可访问URL
     */
    public String uploadImageToLocal(MultipartFile file, Long userId) throws IOException {
        // 1. 校验文件类型和大小
        validateImageFile(file);

        // 2. 生成存储路径（根路径/用户ID/日期/）
        String dateDir = DateUtil.format(new Date(), "yyyyMMdd");
        String relativeDir = userId + File.separator + dateDir; // 相对路径：1001/20250520
        String absoluteDir = uploadConfig.getLocalBasePath() + relativeDir; // 绝对路径：D:/jobhub/upload/1001/20250520

        // 3. 创建目录（不存在则创建）
        File dir = new File(absoluteDir);
        if (!dir.exists()) {
            dir.mkdirs(); // 递归创建多级目录
        }

        // 4. 生成随机文件名（避免重复）
        String originalFileName = file.getOriginalFilename();
        String suffix = FileUtil.extName(originalFileName).toLowerCase(); // 后缀：jpg/png
        String fileName = UUID.randomUUID().toString() + "." + suffix; // 文件名：uuid.jpg

        // 5. 保存文件到本地
        File targetFile = new File(absoluteDir + File.separator + fileName);
        file.transferTo(targetFile);

        // 6. 生成可访问URL（访问基础URL + 相对路径 + 文件名）
        String relativePath = relativeDir + File.separator + fileName;
        return uploadConfig.getAccessBaseUrl() + relativePath.replace("\\", "/"); // 替换反斜杠为正斜杠（URL规范）
    }

    /**
     * 校验图片文件（类型+大小）
     */
    private void validateImageFile(MultipartFile file) {
        // 校验文件是否为空
        if (file.isEmpty()) {
            throw new IllegalArgumentException("上传图片不能为空");
        }

        // 校验文件大小
        if (file.getSize() > UploadConfig.MAX_FILE_SIZE) {
            throw new IllegalArgumentException("图片大小不能超过5MB");
        }

        // 校验文件类型（通过后缀）
        String originalFileName = file.getOriginalFilename();
        String suffix = FileUtil.extName(originalFileName).toLowerCase();
        boolean isSupported = false;
        for (String supportedSuffix : UploadConfig.SUPPORTED_IMAGE_SUFFIX) {
            if (supportedSuffix.equals(suffix)) {
                isSupported = true;
                break;
            }
        }
        if (!isSupported) {
            throw new IllegalArgumentException("仅支持JPG/PNG格式图片");
        }
    }
}