package com.cyd.xs.entity.Group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.JsonParser;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@Entity
@Table(name = "`groups`")
public class Group {
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;                // 小组ID

    private String name;            // 小组名称
    private String intro;           // 小组简介
    private String avatar;          // 小组头像URL
    private String activityType;    // 特色活动类型
    private Long creatorId;         // 创建人ID
    private String status;          // 状态（'normal'=正常，'pending'=待审核等）
    private LocalDateTime createdAt; // 创建时间


    private Integer memberCount;  // 通过user_groups表统计

    public Integer getSafeMemberCount() {
        return memberCount != null ? memberCount : 0;
    }

    @Transient
    private String tagsStr;  // 用于接收 GROUP_CONCAT 的结果

    private List<String> tags;    // 通过group_tags表查询
    private Boolean isJoined;     // 通过user_groups表判断


    // 添加 getter/setter 用于 tagsStr 的解析
    public List<String> getTags() {
        if (tags == null && tagsStr != null) {
            // 处理空字符串或null
            if (tagsStr.trim().isEmpty()) {
                tags = new ArrayList<>();
            } else {
                // 如果是逗号分隔的字符串
                tags = Arrays.asList(tagsStr.split(","));
            }
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
        if (tags != null && !tags.isEmpty()) {
            this.tagsStr = String.join(",", tags);
        }
    }
}