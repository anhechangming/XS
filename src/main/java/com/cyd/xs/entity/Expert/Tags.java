package com.cyd.xs.entity.Expert;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tags") // 与数据库表名一致
public class Tags {
    @TableId(type = IdType.AUTO)
    private Long id;    // 标签ID（关联entity_tags表的tag_id）
    private String tag; // 标签名称（对应数据库的tag字段，如“简历优化”）

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}