package com.cyd.xs.entity.Topic.ChatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chatroom_roles")
public class ChatRoomRoles {
    @Id
    private String id;
    private String chatroomId;
    private String role;
    private Integer userId;
    private LocalDateTime assignedAt;
}
