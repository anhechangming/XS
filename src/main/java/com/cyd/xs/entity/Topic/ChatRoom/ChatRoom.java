package com.cyd.xs.entity.Topic.ChatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chatrooms")
public class ChatRoom {
    @Id
    private Long id;

    private String title;
    private String theme;
    private String status; // preview/ongoing/ended
    private Long hostId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notice;
    private Long topicId;
    private String scope;//可见范围
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}