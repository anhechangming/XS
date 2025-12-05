package com.cyd.xs.dto.Topic;

import java.util.List;

public class TopicPostRequest {
    private Long userId;
    private Long topicId;
    private String content;
    private List<String> images; // 图片列表（base64编码或图片URL）
    private List<String> tags;   // 标签列表


}
