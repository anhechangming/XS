package com.cyd.xs.dto.expert.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Arrays;
import java.util.List;

/**
 * 发布/编辑内容请求DTO
 */
@Data
public class ExpertContentOperateDTO {
    // 必选字段
    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 50, message = "标题长度需在1-50字符之间")
    private String title;

    @NotBlank(message = "内容类型不能为空")
    private String type; // video/article/ppt/audio

    @NotBlank(message = "标签不能为空")
    private String tag; // 职业规划/简历优化/面试技巧/职场适应/心理咨询

    @NotBlank(message = "内容正文/URL不能为空")
    private String content;

    @NotBlank(message = "内容简介不能为空")
    @Length(min = 10, max = 200, message = "简介长度需在10-200字符之间")
    private String desc;

    // 可选字段（视频/PPT/音频类型必传）
    private String coverImage;

    // 校验类型合法性（自定义校验逻辑）
    public boolean validateType() {
        List<String> validTypes = Arrays.asList("video", "article", "ppt", "audio");
        return validTypes.contains(type);
    }

    // 校验标签合法性
    public boolean validateTag() {
        List<String> validTags = Arrays.asList("职业规划", "简历优化", "面试技巧", "职场适应", "心理咨询");
        return validTags.contains(tag);
    }

    // 校验封面图（视频/PPT/音频类型必传）
    public boolean validateCoverImage() {
        List<String> needCoverTypes = Arrays.asList("video", "ppt", "audio");
        if (needCoverTypes.contains(type)) {
            return coverImage != null && !coverImage.isBlank();
        }
        return true; // 文章类型无需封面
    }

    public @NotBlank(message = "标题不能为空") @Length(min = 1, max = 50, message = "标题长度需在1-50字符之间") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "标题不能为空") @Length(min = 1, max = 50, message = "标题长度需在1-50字符之间") String title) {
        this.title = title;
    }

    public @NotBlank(message = "内容类型不能为空") String getType() {
        return type;
    }

    public void setType(@NotBlank(message = "内容类型不能为空") String type) {
        this.type = type;
    }

    public @NotBlank(message = "标签不能为空") String getTag() {
        return tag;
    }

    public void setTag(@NotBlank(message = "标签不能为空") String tag) {
        this.tag = tag;
    }

    public @NotBlank(message = "内容正文/URL不能为空") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "内容正文/URL不能为空") String content) {
        this.content = content;
    }

    public @NotBlank(message = "内容简介不能为空") @Length(min = 10, max = 200, message = "简介长度需在10-200字符之间") String getDesc() {
        return desc;
    }

    public void setDesc(@NotBlank(message = "内容简介不能为空") @Length(min = 10, max = 200, message = "简介长度需在10-200字符之间") String desc) {
        this.desc = desc;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }
}