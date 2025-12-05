package com.cyd.xs.entity.Group;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "group_members")
public class GroupMember {
    @Id
    private Long id;

    private Long groupId;
    private Long userId;
    private String role; // member/manager/creator
    private LocalDateTime joinTime;

}