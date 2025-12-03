package com.cyd.xs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyd.xs.entity.User.UserBrowseHistory;

import java.util.List;

public interface UserBrowseHistoryService extends IService<UserBrowseHistory> {
    // 1. 获取当前用户的浏览历史列表（带总数）
    List<UserBrowseHistory> listCurrentUserHistory();
    // 2. 获取单条浏览记录详情（仅自己的）
    UserBrowseHistory getHistoryDetail(Long historyId);
    // 3. 删除单条浏览记录
    boolean deleteHistory(Long historyId);
    // 4. 清空所有浏览记录
    boolean clearAllHistory();
    // 5. 新增浏览记录（用户浏览内容时调用）
    boolean addBrowseHistory(String entityType, String entityId, String metadata);
}