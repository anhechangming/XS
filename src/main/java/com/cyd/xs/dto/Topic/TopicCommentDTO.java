package com.cyd.xs.dto.Topic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TopicCommentDTO {
    private Long commentId;
    private String status; // pending/passed/rejected
    private LocalDateTime submitTime;
}