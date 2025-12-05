package com.cyd.xs.entity.Group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import com.cyd.xs.config.JsonListTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "group_dynamics")
public class GroupDynamic {
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;  // BIGINT类型

    private Long groupId;       // 对应group_id字段
    private Long userId;        // 对应user_id字段
    private String title;       // 对应title字段
    private String content;     // 对应content字段
    private String status;      // 对应status字段
    private Integer likeCount;  // 对应like_count字段
    private Integer commentCount; // 对应comment_count字段

    @TableField(typeHandler = JsonListTypeHandler.class)
    private String imageUrls;   // 对应image_urls字段（JSON）

    @Transient
    private List<String> imageUrlList;

    @TableField(value = "created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdAt; // 对应created_at字段

    @Transient
    private String nickname;    // 需要通过users表查询

    @Transient
    private String avatar;      // 需要通过users表查询

    @TableField(value = "tags", typeHandler = JsonListTypeHandler.class)
    private List<String> tags;  // 对应tags字段 需要通过其他表查询

}