package com.cyd.xs.entity.Group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "group_resources")
public class GroupResource {
    @Id
    @TableId(type = IdType.AUTO)
    private Long id;  // BIGINT类型

    private Long groupId;       // 对应group_id字段
    private String title;       // 对应title字段
    private String type;        // 对应type字段
    private String description; // 对应description字段
    private Long uploaderId;    // 对应uploader_id字段
    private Integer downloadCount; // 对应download_count字段
    private String size;        // 对应size字段
    private String link;        // 对应link字段
    private String status;      // 对应status字段
    private LocalDateTime createdAt; // 对应created_at字段

    // 表中没有但接口需要的字段
    private String uploader;    // 需要通过users表查询
    private String tag;         // 需要从其他地方获取
        // 需要从其他地方获取
}