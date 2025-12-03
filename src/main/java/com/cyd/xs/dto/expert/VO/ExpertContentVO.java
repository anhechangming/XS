package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExpertContentVO {
    private Long id;                // 资源ID
    private String title;           // 资源标题
    private String type;            // 资源类型（video/article/ppt/audio）
    private Double score;           // 评分（取自extra_json）
    private Long viewCount;         // 浏览量（hot_value）
    private Integer collectCount;   // 收藏数（统计）
    private LocalDateTime publishTime; // 发布时间
    private String link;            // 详情链接（示例格式）

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getCollectCount() {
        return collectCount;
    }

    public void setCollectCount(Integer collectCount) {
        this.collectCount = collectCount;
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
