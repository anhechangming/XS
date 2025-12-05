package com.cyd.xs.dto.Home;

import lombok.Data;

import java.util.List;

@Data
public class RecommendRefreshDTO {
    private Long total;
    private Integer pageNum;
    private Integer pageSize;
    private List<ContentItem> list;

    @Data
    public static class ContentItem {
        private Long id;
        private String title;
        private String type;           // article/topic/post
        private String author;
        private String avatarUrl;      // 新增：作者头像
        private Integer likeCount;
        private Integer collectCount;
        private Integer commentCount;  // 新增：评论数
        private String summary;        // 新增：内容摘要
        private String coverImage;     // 新增：封面图
        private String publishTime;
        private String link;
    }
}