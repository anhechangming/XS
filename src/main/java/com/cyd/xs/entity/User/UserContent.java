package com.cyd.xs.entity.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_contents")
public class UserContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "type", nullable = false, length = 32)
    private String type; // topic/post/article

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "pending"; // pending/passed/rejected

    @Column(name = "is_top")
    private Boolean isTop = false;

    @Column(name = "view_count")
    private Integer viewCount = 0;

    @Column(name = "like_count")
    private Integer likeCount = 0;

    @Column(name = "collect_count")
    private Integer collectCount = 0;

    @Column(name = "comment_count")
    private Integer commentCount = 0;

    @Column(name = "cover_image", length = 255)
    private String coverImage;

    @Column(name = "summary", length = 500)
    private String summary;

    @Column(name = "hot_score")
    private Integer hotScore = 0;

    @Column(name = "recommended_for", columnDefinition = "JSON")
    private String recommendedFor;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 关联的用户信息（需要JOIN查询）
    @Transient
    private String authorName;

    @Transient
    private String avatarUrl;
}