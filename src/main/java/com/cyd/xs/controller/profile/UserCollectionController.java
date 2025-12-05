package com.cyd.xs.controller.profile;

import com.cyd.xs.Response.Result;
import com.cyd.xs.entity.User.Collections;
import com.cyd.xs.entity.User.CollectionFolder;
import com.cyd.xs.service.CollectionFolderService;
import com.cyd.xs.service.CollectionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/collection")
public class UserCollectionController {

    private final CollectionFolderService folderService;
    private final CollectionService collectionService;

    public UserCollectionController(CollectionFolderService folderService, CollectionService collectionService) {
        this.folderService = folderService;
        this.collectionService = collectionService;
    }

    /**
     * 功能：页面“我的收藏”左侧文件夹列表（含收藏数）
     * 请求示例：GET /api/user/collection/folders
     */
    @GetMapping("/folders")
    public Result<List<CollectionFolder>> listFolders() {
        List<CollectionFolder> folders = folderService.listUserFolders();
        return Result.success("获取收藏文件夹成功", folders);
    }

    /**
     * 功能：页面“新建收藏夹”按钮
     * 请求示例：POST /api/user/collection/folders?name=简历相关
     */
    @PostMapping("/folders")
    public Result<Void> createFolder(@RequestParam String name) {
        boolean success = folderService.createFolder(name);
        return success ? Result.success("新建收藏夹成功") : Result.error("新建收藏夹失败");
    }

    /**
     * 功能：选择文件夹后，显示对应文件夹下的收藏内容
     * 请求示例：GET /api/user/collection/list?folderId=1
     */
    @GetMapping("/list")
    public Result<List<Collections>> listCollectionByFolder(@RequestParam Long folderId) {
        List<Collections> collections = collectionService.listByFolderId(folderId);
        return Result.success("获取收藏内容成功", collections);
    }

    /**
     * 功能：页面“取消收藏”按钮
     * 请求示例：DELETE /api/user/collection/cancel/1
     */
    @DeleteMapping("/cancel/{collectionId}")
    public Result<Void> cancelCollection(@PathVariable Long collectionId) {
        boolean success = collectionService.cancelCollection(collectionId);
        return success ? Result.success("取消收藏成功") : Result.error("取消收藏失败");
    }
}