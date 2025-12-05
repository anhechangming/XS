package com.cyd.xs.dto.Topic;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EssenceNoteDTO {
    private Long noteId;
    private String noteUrl;
    private LocalDateTime generateTime;
}