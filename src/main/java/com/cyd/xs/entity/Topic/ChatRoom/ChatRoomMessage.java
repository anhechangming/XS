package com.cyd.xs.entity.Topic.ChatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chat_room_messages")
public class ChatRoomMessage {
    @Id
    private String id;

    private String chatRoomId;
    private String userId;
    //private String avatar;
    private String content;
    private LocalDateTime sendTime;
}