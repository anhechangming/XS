package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyd.xs.Utils.SecurityUtils;
import com.cyd.xs.entity.User.Collections;
import com.cyd.xs.mapper.CollectionMapper;
import com.cyd.xs.service.CollectionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 实现类
@Service
public class CollectionServiceImpl extends ServiceImpl<CollectionMapper, Collections>
        implements CollectionService {

    @Override
    public List<Collections> listByFolderId(Long folderId) {
        Long userId = SecurityUtils.getUserId();
        return list(Wrappers.<Collections>lambdaQuery()
                .eq(Collections::getUserId, userId)
                .eq(Collections::getFolderId, folderId)
                .orderByDesc(Collections::getCreatedAt)
        );
    }

    @Override
    public boolean cancelCollection(Long collectionId) {
        Long userId = SecurityUtils.getUserId();
        // 仅允许取消自己的收藏
        Boolean affectedRows = remove(Wrappers.<Collections>lambdaQuery()
                .eq(Collections::getId, collectionId)
                .eq(Collections::getUserId, userId)
        );
        return affectedRows;
    }

    @Override
    public boolean addCollection(Collections collection) {
        Long userId = SecurityUtils.getUserId();
        collection.setUserId(userId);
        collection.setCreatedAt(LocalDateTime.now());
        return save(collection);
    }
}