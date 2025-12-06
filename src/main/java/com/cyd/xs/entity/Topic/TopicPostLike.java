package com.cyd.xs.entity.Topic;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "topic_post_like")
public class TopicPostLike {
    @Id

    private Long id;
    private Long postId;
    private Long userId;
    private LocalDateTime createdAt;

}
