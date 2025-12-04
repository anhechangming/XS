package com.cyd.xs.entity.Group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "group_tag")
public class GroupTag {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long groupId;
    private String tag;

}
