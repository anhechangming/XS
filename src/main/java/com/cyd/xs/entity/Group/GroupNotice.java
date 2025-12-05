package com.cyd.xs.entity.Group;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "group_notices")
public class GroupNotice {
    @Id
    private Long id;

    private Long groupId;
    private String title;
    private String content;
    private LocalDateTime createdAt;


}