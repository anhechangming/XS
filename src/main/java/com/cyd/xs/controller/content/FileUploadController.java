package com.cyd.xs.controller.content;

import com.cyd.xs.Utils.FileUploadUtil;
import com.cyd.xs.Utils.ResultVO;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用文件上传接口（PDF/Word/PPT）
 */
@RestController
@RequestMapping("/api/v1/upload")
public class FileUploadController {
    @Resource
    private FileUploadUtil fileUploadUtil;

    /**
     * 上传文件（单次1个，支持PDF/Word/PPT）
     */
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO<FileUploadResponseVO> uploadFile(
            @RequestParam("file") MultipartFile file, // form-data参数名：file
            Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.error(401, "请先登录");
        }

        // 2. 获取登录用户ID
        String userIdStr = (String) authentication.getPrincipal();
        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return ResultVO.error(400, "用户ID格式错误");
        }

        // 3. 校验文件是否为空
        if (file == null || file.isEmpty()) {
            return ResultVO.error(400, "请选择要上传的文件");
        }

        try {
            // 4. 上传文件（调用工具类）
            FileUploadUtil.FileUploadResult uploadResult = fileUploadUtil.uploadFileToLocal(file, userId);

            // 5. 组装返回VO（匹配需求格式）
            FileUploadResponseVO responseVO = new FileUploadResponseVO();
            responseVO.setFileUrl(uploadResult.getFileUrl());
            responseVO.setFileName(uploadResult.getFileName());
            responseVO.setFileSize(uploadResult.getFileSize());

            return ResultVO.success("文件上传成功", responseVO);
        } catch (IllegalArgumentException e) {
            // 6. 处理参数校验异常（格式/大小错误）
            return ResultVO.error(400, e.getMessage());
        } catch (Exception e) {
            // 7. 处理系统异常（文件写入失败等）
            return ResultVO.error(500, "文件上传失败：" + e.getMessage());
        }
    }

    /**
     * 文件上传返回VO（匹配需求示例格式）
     */
    public static class FileUploadResponseVO {
        private String fileUrl;    // 文件访问URL
        private String fileName;   // 原始文件名
        private String fileSize;   // 格式化文件大小（如2.5MB）

        // getter/setter（必须有，否则Jackson无法序列化）
        public String getFileUrl() { return fileUrl; }
        public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
        public String getFileName() { return fileName; }
        public void setFileName(String fileName) { this.fileName = fileName; }
        public String getFileSize() { return fileSize; }
        public void setFileSize(String fileSize) { this.fileSize = fileSize; }
    }
}