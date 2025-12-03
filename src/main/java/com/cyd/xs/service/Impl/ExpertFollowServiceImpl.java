package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cyd.xs.dto.expert.VO.FollowResultVO;
import com.cyd.xs.entity.Expert.Expert;
import com.cyd.xs.entity.Expert.UserFollow;
import com.cyd.xs.mapper.ExpertMapper;
import com.cyd.xs.mapper.UserFollowMapper;
import com.cyd.xs.service.ExpertFollowService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExpertFollowServiceImpl implements ExpertFollowService {

    @Resource
    private ExpertMapper expertMapper;

    @Resource
    private UserFollowMapper userFollowMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FollowResultVO followExpert(Long expertId, Boolean isFollow, Long userId) {
        // 1. 校验专家是否存在
        Expert expert = expertMapper.selectById(expertId);
        if (expert == null) {
            throw new RuntimeException("专家不存在");
        }
        Long expertUserId = expert.getUserId(); // 专家关联的users表ID（target_user_id）

        // 2. 查询当前用户的关注记录
        UserFollow followRecord = userFollowMapper.getFollowRecord(userId, expertUserId);

        // 3. 处理关注/取消逻辑
        if (isFollow) {
            // 3.1 关注操作
            handleFollow(userId, expertUserId, followRecord);
        } else {
            // 3.2 取消关注操作
            handleUnfollow(userId, expertUserId, followRecord);
        }

        // 4. 统计专家的总关注数
        Integer followCount = userFollowMapper.countFollowByExpertId(expertUserId);

        // 5. 组装返回结果
        FollowResultVO resultVO = new FollowResultVO();
        resultVO.setExpertId(expertId);
        resultVO.setFollowed(isFollow);
        resultVO.setFollowCount(followCount);
        return resultVO;
    }

    // 关注逻辑
    private void handleFollow(Long userId, Long expertUserId, UserFollow followRecord) {
        if (followRecord == null) {
            // 无记录：新增关注
            UserFollow newFollow = new UserFollow();
            newFollow.setUserId(userId);
            newFollow.setTargetUserId(expertUserId);
            newFollow.setIsFollow(1); // 1=关注
            userFollowMapper.insert(newFollow);
        } else {
            // 有记录：更新为关注状态
            if (followRecord.getIsFollow() != 1) {
                userFollowMapper.update(null, new LambdaUpdateWrapper<UserFollow>()
                        .eq(UserFollow::getUserId, userId)
                        .eq(UserFollow::getTargetUserId, expertUserId)
                        .set(UserFollow::getIsFollow, 1));
            }
        }
    }

    // 取消关注逻辑
    private void handleUnfollow(Long userId, Long expertUserId, UserFollow followRecord) {
        if (followRecord != null) {
            // 有记录：更新为取消状态
            if (followRecord.getIsFollow() == 1) {
                userFollowMapper.update(null, new LambdaUpdateWrapper<UserFollow>()
                        .eq(UserFollow::getUserId, userId)
                        .eq(UserFollow::getTargetUserId, expertUserId)
                        .set(UserFollow::getIsFollow, 0));
            }
        }
        // 无记录：无需操作
    }
}