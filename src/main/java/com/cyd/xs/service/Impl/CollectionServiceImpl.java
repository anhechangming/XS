package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyd.xs.Utils.SecurityUtils;
import com.cyd.xs.entity.User.Collection;
import com.cyd.xs.mapper.CollectionMapper;
import com.cyd.xs.service.CollectionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 实现类
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collection>
        implements CollectionService {

    @Override
    public List<Collection> listByFolderId(Long folderId) {
        Long userId = SecurityUtils.getUserId();
        return list(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getUserId, userId)
                .eq(Collection::getFolderId, folderId)
                .orderByDesc(Collection::getCreatedAt)
        );
    }

    @Override
    public boolean cancelCollection(Long collectionId) {
        Long userId = SecurityUtils.getUserId();
        // 仅允许取消自己的收藏
        Boolean affectedRows = remove(Wrappers.<Collection>lambdaQuery()
                .eq(Collection::getId, collectionId)
                .eq(Collection::getUserId, userId)
        );
        return affectedRows;
    }

    @Override
    public boolean addCollection(Collection collection) {
        Long userId = SecurityUtils.getUserId();
        collection.setUserId(userId);
        collection.setCreatedAt(LocalDateTime.now());
        return save(collection);
    }
}