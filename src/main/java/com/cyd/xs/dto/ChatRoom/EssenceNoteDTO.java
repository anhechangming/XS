package com.cyd.xs.dto.ChatRoom;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EssenceNoteDTO {
    private Long noteId;
    private Long chatRoomId;
    private String title;
    private String summary;
    private String content;  // 或者可以是HTML内容
    private String noteUrl;
    private Integer messageCount;
    private LocalDateTime generateTime;
    private LocalDateTime createdAt;

    // 生成者信息
    private Long userId;
    private String username;
    private String avatarUrl;
}