package com.cyd.xs.controller.content;

import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.content.DTO.QuickPublishDTO;
import com.cyd.xs.dto.content.VO.QuickPublishVO;
import com.cyd.xs.service.QuickPublishService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 快捷发布内容接口（话题/帖子/文章）
 */
@RestController
@RequestMapping("/api/v1")
@Validated
public class QuickPublishController {

    @Resource
    private QuickPublishService publishService;

    /**
     * 快捷发布内容
     */
    @PostMapping(value = "/publish", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<QuickPublishVO> quickPublish(
            @Valid @RequestBody QuickPublishDTO dto,
            Authentication authentication) {

        // 1. 校验登录状态（未登录无法发布）
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.error(401, "请先登录");
        }

        // 2. 获取登录用户ID（Principal是String类型的用户ID）
        String userIdStr = (String) authentication.getPrincipal();
        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return ResultVO.error(400, "用户ID格式错误");
        }

        try {
            // 3. 调用Service发布内容
            QuickPublishVO vo = publishService.publish(userId, dto);
            return ResultVO.success("内容发布成功，待审核", vo);
        } catch (IllegalArgumentException e) {
            // 4. 处理参数校验异常
            return ResultVO.error(400, e.getMessage());
        } catch (Exception e) {
            // 5. 处理系统异常
            return ResultVO.error(500, "内容发布失败：" + e.getMessage());
        }
    }
}