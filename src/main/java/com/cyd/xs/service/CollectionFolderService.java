package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.entity.User.CollectionFolder;

import java.util.List;

public interface CollectionFolderService extends IService<CollectionFolder> {
    // 1. 获取当前用户的所有收藏文件夹（含每个文件夹的收藏数）
    List<CollectionFolder> listUserFolders();
    // 2. 新建收藏文件夹
    boolean createFolder(String folderName);
}