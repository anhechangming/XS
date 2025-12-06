package com.cyd.xs.entity.Topic.ChatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "essence_notes")
public class EssenceNote {
    @Id
    private Long id;

    private Long chatRoomId;
    private Long userId;
    private String title;
    private String content;
    private String summary;
    private Integer messageCount;
    private LocalDateTime generateTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}