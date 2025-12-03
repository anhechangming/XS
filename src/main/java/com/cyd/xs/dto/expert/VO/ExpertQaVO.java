package com.cyd.xs.dto.expert.VO;

import java.time.LocalDateTime;

public class ExpertQaVO {
    private Long id;             // 答疑ID
    private String title;        // 答疑标题
    private Integer replyCount;  // 回复数
    private Long viewCount;      // 浏览量
    private LocalDateTime publishTime; // 发布时间
    private String link;         // 答疑详情链接（拼接格式）

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
