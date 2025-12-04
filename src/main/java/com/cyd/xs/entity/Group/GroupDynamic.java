package com.cyd.xs.entity.Group;

import cn.hutool.core.lang.TypeReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "group_dynamics")
public class GroupDynamic {
    @Id
    private Long id;  // BIGINT类型

    private Long groupId;       // 对应group_id字段
    private Long userId;        // 对应user_id字段
    private String title;       // 对应title字段
    private String content;     // 对应content字段
    private String status;      // 对应status字段
    private Integer likeCount;  // 对应like_count字段
    private Integer commentCount; // 对应comment_count字段
    private String imageUrls;   // 对应image_urls字段（JSON）
    private LocalDateTime createdAt; // 对应created_at字段

    @Transient
    private List<String> imageUrlList;

    @Column(name = "tags")
    private String tagsStr;  // JSON字符串

    // 表中没有但接口需要的字段
    private String nickname;    // 需要通过users表查询
    private String avatar;      // 需要通过users表查询
    private List<String> tags;  // 需要通过其他表查询

    public List<String> getImageUrlList() {
        if (imageUrlList == null &&imageUrls != null) {
            try {
                imageUrlList = objectMapper.readValue(imageUrls, new TypeReference<List<String>>() {});
            }catch (Exception e) {
                imageUrlList = Arrays.asList(imageUrls.split(","));
            }
        }
        return imageUrlList;
    }

    public List<String> getTags() {
        if (tags == null && tagsStr != null) {
            try {
                tags = objectMapper.readValue(tagsStr, new TypeReference<List<String>>() {});
            } catch (Exception e) {
                // 如果解析失败，尝试逗号分隔
                tags = Arrays.asList(tagsStr.split(","));
            }
        }
        return tags;
    }

}