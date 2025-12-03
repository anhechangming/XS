package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.entity.User.Entity;

import java.util.List;

public interface EntityService extends IService<Entity> {
    // 1. 获取当前用户“我发布的”内容列表
    List<Entity> listMyPublishedContent();
    // 2. 发布新内容（发帖）
    boolean publishContent(Entity entity);
    // 3. 编辑已发布内容
    boolean updateContent(Entity entity);
    // 4. 删除已发布内容（逻辑删除/物理删除，这里用物理删除）
    boolean deleteContent(Long entityId);
    // 5. 置顶/取消置顶内容
    boolean updateTopStatus(Long entityId, Integer isTop);
}
