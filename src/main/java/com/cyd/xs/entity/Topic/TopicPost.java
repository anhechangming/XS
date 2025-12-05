package com.cyd.xs.entity.Topic;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "topic_posts")
public class TopicPost {
    @Id
    private Long id;

    @Column(nullable = false)
    private Long topicId;

    @Column(nullable = false)
    private Long userId;

    private String userName;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "JSON")
    private String images;

    @Column(columnDefinition = "JSON")
    private String tags;

    private Integer likeCount = 0;
    private Integer commentCount = 0;
    private Integer collectCount = 0;
    private LocalDateTime createdAt;

    public TopicPost() {
    }

    public TopicPost(Long id, Long topicId, Long userId, String userName, String content, String images, String tags, Integer likeCount, Integer commentCount, Integer collectCount, LocalDateTime createdAt) {
        this.id = id;
        this.topicId = topicId;
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.images = images;
        this.tags = tags;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.collectCount = collectCount;
        this.createdAt = createdAt;
    }


}
