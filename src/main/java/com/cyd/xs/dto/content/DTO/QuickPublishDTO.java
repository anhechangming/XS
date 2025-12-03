package com.cyd.xs.dto.content.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 快捷发布内容请求DTO
 */
@Data
public class QuickPublishDTO {
    // 必选字段
    @NotBlank(message = "内容类型不能为空")
    private String type; // topic（话题）、post（帖子）、article（文章）

    @NotBlank(message = "标题不能为空")
    @Length(min = 1, max = 50, message = "标题长度需在1-50字符之间")
    private String title;

    @NotBlank(message = "内容正文不能为空")
    @Length(min = 1, max = 2000, message = "内容正文长度需在1-2000字符之间")
    private String content;

    // 可选字段（话题/文章类型必传）
    @Size(min = 1, max = 3, message = "标签需1-3个")
    private List<String> tags;

    // 可选字段（最多4张图片）
    @Size(max = 4, message = "最多上传4张图片")
    private List<String> imageUrls;

    // 可选字段（发布小组帖子时必传）
    private Long groupId;

    /**
     * 按类型校验差异化参数
     */
    public void validateByType() {
        // 1. 校验类型合法性
        List<String> validTypes = List.of("topic", "post", "article");
        if (!validTypes.contains(type)) {
            throw new IllegalArgumentException("内容类型不合法（仅支持topic/post/article）");
        }

        // 2. 话题/文章类型必传标签
        if (("topic".equals(type) || "article".equals(type)) && (tags == null || tags.isEmpty())) {
            throw new IllegalArgumentException("话题/文章类型必须传入1-3个标签");
        }

        // 3. 小组帖子（post类型且传了groupId）需确保groupId合法
        if ("post".equals(type) && groupId != null && groupId <= 0) {
            throw new IllegalArgumentException("小组ID不合法");
        }
    }

    public @NotBlank(message = "内容类型不能为空") String getType() {
        return type;
    }

    public void setType(@NotBlank(message = "内容类型不能为空") String type) {
        this.type = type;
    }

    public @NotBlank(message = "标题不能为空") @Length(min = 1, max = 50, message = "标题长度需在1-50字符之间") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "标题不能为空") @Length(min = 1, max = 50, message = "标题长度需在1-50字符之间") String title) {
        this.title = title;
    }

    public @NotBlank(message = "内容正文不能为空") @Length(min = 1, max = 2000, message = "内容正文长度需在1-2000字符之间") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "内容正文不能为空") @Length(min = 1, max = 2000, message = "内容正文长度需在1-2000字符之间") String content) {
        this.content = content;
    }

    public @Size(min = 1, max = 3, message = "标签需1-3个") List<String> getTags() {
        return tags;
    }

    public void setTags(@Size(min = 1, max = 3, message = "标签需1-3个") List<String> tags) {
        this.tags = tags;
    }

    public @Size(max = 4, message = "最多上传4张图片") List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(@Size(max = 4, message = "最多上传4张图片") List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }
}