package com.cyd.xs.dto.Topic;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class EssenceNoteRequest {
    private String title;  // 笔记标题
    private String summary;  // 摘要
    private List<Long> messageIds;  // 包含的消息ID列表
    private String content;  // 自定义内容（可选）
}