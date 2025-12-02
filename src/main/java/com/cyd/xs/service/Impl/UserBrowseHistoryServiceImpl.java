package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyd.xs.Utils.SecurityUtils;
import com.cyd.xs.entity.User.UserBrowseHistory;
import com.cyd.xs.mapper.UserBrowseHistoryMapper;
import com.cyd.xs.service.UserBrowseHistoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
/*
* 浏览记录服务实现类
 */
@Service
public class UserBrowseHistoryServiceImpl extends ServiceImpl<UserBrowseHistoryMapper, UserBrowseHistory>
        implements UserBrowseHistoryService {

    @Override
    public List<UserBrowseHistory> listCurrentUserHistory() {
        // 从 SecurityUtils 获取当前登录用户ID
        Long userId = SecurityUtils.getUserId();
        return baseMapper.listByUserId(userId);
    }

    @Override
    public UserBrowseHistory getHistoryDetail(Long historyId) {
        Long userId = SecurityUtils.getUserId();
        // 仅能查询自己的浏览记录（避免越权）
        return baseMapper.getByIdAndUserId(historyId, userId);
    }

    @Override
    public boolean deleteHistory(Long historyId) {
        Long userId = SecurityUtils.getUserId();
        int affectedRows = baseMapper.deleteByIdAndUserId(historyId, userId);
        return affectedRows > 0; // 受影响行数>0 表示删除成功
    }

    @Override
    public boolean clearAllHistory() {
        Long userId = SecurityUtils.getUserId();
        int affectedRows = baseMapper.deleteAllByUserId(userId);
        return affectedRows > 0;
    }

    @Override
    public boolean addBrowseHistory(String entityType, String entityId, String metadata) {
        Long userId = SecurityUtils.getUserId();
        // 构建浏览记录对象
        UserBrowseHistory history = new UserBrowseHistory();
        history.setUserId(userId);
        history.setEntityType(entityType); // 内容类型：post/topic/group
        history.setEntityId(entityId);     // 内容ID（对应实体的主键）
        history.setMetadata(metadata);     // 扩展元数据（JSON字符串）
        history.setViewedAt(LocalDateTime.now()); // 浏览时间（当前时间）
        return save(history);
    }
}