package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResourceVO {
    private Long id;                // 资源ID
    private String title;           // 资源标题
    private String tag;             // 标签（如“简历优化”）
    private String level;           // 资源等级（A/S）
    private String desc;            // 资源简介（取自 extra_json 的 desc 字段）
    private String author;          // 专家姓名
    private Double score;           // 评分（取自 extra_json 的 score 字段）
    private Long viewCount;         // 浏览量（取自 hot_value）
    private Integer collectCount;   // 收藏数（需关联收藏表统计，这里简化为 0）
    private Integer likeCount;      // 点赞数（关联entity_likes表统计）
    private LocalDateTime publishTime; // 发布时间
    private String type;            // 资源类型（video/article/ppt/audio）
    private String coverImage;      // 封面图（cover_image字段）
    private String link;            // 资源详情链接（拼接资源ID）

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
