package com.cyd.xs.dto.Group;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupCreateResultDTO {
    private Long groupId;
    private String status;
    private LocalDateTime submitTime;


}