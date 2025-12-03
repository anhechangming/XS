package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.entity.User.Collection;

import java.util.List;

public interface CollectionService extends IService<Collection> {
    // 1. 获取指定文件夹下的收藏内容
    List<Collection> listByFolderId(Long folderId);
    // 2. 取消收藏
    boolean cancelCollection(Long collectionId);
    // 3. 收藏内容（可选，页面可能需要）
    boolean addCollection(Collection collection);

}
