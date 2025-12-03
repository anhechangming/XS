package com.cyd.xs.controller.content;



import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.Utils.UploadUtil;
import com.cyd.xs.config.UploadConfig;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用图片上传接口
 */
@RestController
@RequestMapping("/api/v1/upload")
public class UploadController {
    @Resource
    private UploadUtil uploadUtil;

    /**
     * 上传图片（支持多图，最多4张）
     */
    @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResultVO<ImageUploadVO> uploadImage(
            @RequestParam("image") MultipartFile[] files, // 接收多文件（form-data参数名image）
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

        // 3. 校验文件数量（最多4张）
        if (files == null || files.length == 0) {
            return ResultVO.error(400, "请选择要上传的图片");
        }
        if (files.length > UploadConfig.MAX_FILE_COUNT) {
            return ResultVO.error(400, "单次最多上传4张图片");
        }

        try {
            // 4. 批量上传图片
            List<String> imageUrls = new ArrayList<>();
            for (MultipartFile file : files) {
                String imageUrl = uploadUtil.uploadImageToLocal(file, userId);
                imageUrls.add(imageUrl);
            }

            // 5. 组装返回结果
            ImageUploadVO vo = new ImageUploadVO();
            vo.setImageUrls(imageUrls);
            return ResultVO.success("图片上传成功", vo);
        } catch (IllegalArgumentException e) {
            // 6. 处理参数校验异常
            return ResultVO.error(400, e.getMessage());
        } catch (Exception e) {
            // 7. 处理系统异常（如文件写入失败）
            return ResultVO.error(500, "图片上传失败：" + e.getMessage());
        }
    }

    /**
     * 图片上传返回VO
     */
    public static class ImageUploadVO {
        private List<String> imageUrls; // 图片访问URL列表

        // getter/setter
        public List<String> getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(List<String> imageUrls) {
            this.imageUrls = imageUrls;
        }
    }
}