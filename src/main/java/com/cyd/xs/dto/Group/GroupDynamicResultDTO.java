package com.cyd.xs.dto.Group;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GroupDynamicResultDTO {
    private Long dynamicId;
    private String status;
    private LocalDateTime submitTime;


}