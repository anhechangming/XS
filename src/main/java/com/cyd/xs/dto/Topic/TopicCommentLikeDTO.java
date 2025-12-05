package com.cyd.xs.dto.Topic;

import lombok.Data;

@Data
public class TopicCommentLikeDTO {
    private Long commentId;
    private Integer likeCount;
    private Boolean isLike;
}