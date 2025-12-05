package com.cyd.xs.dto.Group;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupResourceResultDTO {
    private Long resourceId;
    private String status;
    private LocalDateTime submitTime;
}