package com.cyd.xs.controller.expert;

import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.expert.VO.FollowResultVO;
import com.cyd.xs.service.ExpertFollowService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 专家关注接口
 */
@RestController
@RequestMapping("/api/v1/expert")
public class ExpertFollowController {

    @Resource
    private ExpertFollowService expertFollowService;

    /**
     * 关注/取消关注专家
     * @param id 专家ID（路径参数）
     * @param // isFollow 是否关注（请求体参数）
     * @param authentication Spring Security认证信息（获取当前用户ID）
     * @return 关注结果
     */
    @PutMapping(value = "/{id}/follow", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<FollowResultVO> followExpert(
            @PathVariable("id") Long id,
            @RequestBody FollowRequestDTO requestDTO,
            Authentication authentication) {

        // 1. 获取当前登录用户ID（JWT中解析的userId）
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.fail("请先登录");
        }
        Long userId = Long.parseLong(authentication.getName());

        // 2. 调用Service处理关注逻辑
        FollowResultVO resultVO = expertFollowService.followExpert(id, requestDTO.getIsFollow(), userId);

        // 3. 组装返回结果
        String message = requestDTO.getIsFollow() ? "关注成功" : "取消关注成功";
        return ResultVO.success(message, resultVO);
    }
}

// 定义请求体DTO（接收isFollow参数）
class FollowRequestDTO {
    private Boolean isFollow;

    // getter/setter
    public Boolean getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Boolean isFollow) {
        this.isFollow = isFollow;
    }
}