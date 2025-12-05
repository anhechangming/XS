package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.entity.User.Collections;

import java.util.List;

public interface CollectionService extends IService<Collections> {
    // 1. 获取指定文件夹下的收藏内容
    List<Collections> listByFolderId(Long folderId);
    // 2. 取消收藏
    boolean cancelCollection(Long collectionId);
    // 3. 收藏内容（可选，页面可能需要）
    boolean addCollection(Collections collection);

}
