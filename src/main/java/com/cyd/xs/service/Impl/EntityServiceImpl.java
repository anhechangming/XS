package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyd.xs.Utils.SecurityUtils;
import com.cyd.xs.entity.User.Entity;
import com.cyd.xs.mapper.EntityMapper;
import com.cyd.xs.service.EntityService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EntityServiceImpl extends ServiceImpl<EntityMapper, Entity> implements EntityService {

    @Override
    public List<Entity> listMyPublishedContent() {
        Long authorId = SecurityUtils.getUserId(); // 从SecurityUtils获取当前用户ID
        return baseMapper.listPublishedByAuthorId(authorId);
    }

    @Override
    public boolean publishContent(Entity entity) {
        Long authorId = SecurityUtils.getUserId();
        // 填充发布者ID、状态、时间等默认值
        entity.setAuthorId(authorId);
        entity.setStatus("PUBLISHED");
        entity.setIsHot(0);
        entity.setIsTop(0);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        return save(entity);
    }

    @Override
    public boolean updateContent(Entity entity) {
        Long authorId = SecurityUtils.getUserId();
        // 仅允许编辑自己的内容
        Entity existing = getById(entity.getId());
        if (existing == null || !existing.getAuthorId().equals(authorId)) {
            throw new RuntimeException("无权限编辑该内容");
        }
        entity.setUpdatedAt(LocalDateTime.now());
        return updateById(entity);
    }

    @Override
    public boolean deleteContent(Long entityId) {
        Long authorId = SecurityUtils.getUserId();
        // 仅允许删除自己的内容
        Entity existing = getById(entityId);
        if (existing == null || !existing.getAuthorId().equals(authorId)) {
            throw new RuntimeException("无权限删除该内容");
        }
        return removeById(entityId);
    }

    @Override
    public boolean updateTopStatus(Long entityId, Integer isTop) {
        Long authorId = SecurityUtils.getUserId();
        // 仅允许操作自己的内容
        Entity existing = getById(entityId);
        if (existing == null || !existing.getAuthorId().equals(authorId)) {
            throw new RuntimeException("无权限操作该内容");
        }
        // 0=取消置顶，1=置顶
        int affectedRows = baseMapper.updateTopStatus(entityId, authorId, isTop);
        return affectedRows > 0;
    }
}