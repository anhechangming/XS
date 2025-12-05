package com.cyd.xs.dto.Group;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupJoinDTO {
    private Long groupId;
    private LocalDateTime joinTime;
    private Integer memberCount ;

}