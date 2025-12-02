package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostVO {
    private Long id;             // 内容ID
    private String title;        // 文章标题
    private Integer replyCount;  // 回复数（简化：暂写0，若后续有评论表可关联）
    private Long viewCount;      // 浏览量（hot_value字段）
    private Integer likeCount;   // 点赞数（关联entity_likes表统计）
    private LocalDateTime publishTime; // 发布时间（created_at字段）
    private String link;         // 文章详情链接（拼接格式）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(LocalDateTime publishTime) {
        this.publishTime = publishTime;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}