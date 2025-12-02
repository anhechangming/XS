package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyd.xs.Utils.SecurityUtils;
import com.cyd.xs.entity.User.CollectionFolder;
import com.cyd.xs.mapper.CollectionFolderMapper;
import com.cyd.xs.mapper.CollectionMapper;
import com.cyd.xs.service.CollectionFolderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

// 实现类
@Service
public class CollectionFolderServiceImpl extends ServiceImpl<CollectionFolderMapper, CollectionFolder>
        implements CollectionFolderService {

    private final CollectionFolderMapper folderMapper;
    private final CollectionMapper collectionMapper;

    public CollectionFolderServiceImpl(CollectionFolderMapper folderMapper, CollectionMapper collectionMapper) {
        this.folderMapper = folderMapper;
        this.collectionMapper = collectionMapper;
    }

    @Override
    public List<CollectionFolder> listUserFolders() {
        Long userId = SecurityUtils.getUserId();
        List<CollectionFolder> folders = folderMapper.listByUserId(userId);
        // 为每个文件夹添加收藏数
        folders.forEach(folder -> {
            Integer count = collectionMapper.countCollectionsInFolder(folder.getId(), userId);
            // 可在Folder实体中加count字段，或用VO包装，这里简化处理
            folder.setName(folder.getName() + " (" + count + ")");
        });
        return folders;
    }

    @Override
    public boolean createFolder(String folderName) {
        Long userId = SecurityUtils.getUserId();
        CollectionFolder folder = new CollectionFolder();
        folder.setUserId(userId);
        folder.setName(folderName);
        folder.setCreatedAt(LocalDateTime.now());
        return save(folder);
    }
}