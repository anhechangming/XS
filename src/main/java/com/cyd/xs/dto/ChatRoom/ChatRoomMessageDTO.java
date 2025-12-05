package com.cyd.xs.dto.ChatRoom;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatRoomMessageDTO {
    private Long messageId;
    private LocalDateTime sendTime;
}