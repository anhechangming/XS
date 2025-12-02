package com.cyd.xs.dto.expert.VO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * 专家内容管理列表项VO
 */
@Data
public class ExpertContentItemVO {
    private Long id;                // 内容ID
    private String title;           // 内容标题
    private String type;            // 内容类型（video/article/ppt/audio）
    private String status = "passed";          // 状态（pending/passed/rejected）
    private Double score;           // 评分（从extra_json解析）
    private Long viewCount;         // 浏览量（hot_value）
    private Integer collectCount;   // 收藏数（统计）
    private LocalDateTime publishTime; // 发布时间
    private List<String> operation; // 可操作项（固定返回["edit", "update", "delete"]）

    // 构造方法：初始化可操作项
    public ExpertContentItemVO() {
        this.operation = Arrays.asList("edit", "update", "delete");
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public List<String> getOperation() {
        return operation;
    }

    public void setOperation(List<String> operation) {
        this.operation = operation;
    }
}