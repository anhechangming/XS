package com.cyd.xs.controller.expert;

import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.expert.DTO.ExpertContentOperateDTO;
import com.cyd.xs.dto.expert.VO.ExpertContentOperateVO;
import com.cyd.xs.entity.Expert.Expert;
import com.cyd.xs.mapper.ExpertMapper;
import com.cyd.xs.service.ExpertContentOperateService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 专家内容发布/编辑接口
 */
@RestController
@RequestMapping("/api/v1/expert/backend/content")
@Validated
public class ExpertContentOperateController {

    @Resource
    private ExpertContentOperateService contentOperateService;

    @Resource
    private ExpertMapper expertMapper;

    /**
     * 发布新内容
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<ExpertContentOperateVO> publishContent(
            @Valid @RequestBody ExpertContentOperateDTO dto,
            Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.fail("请先登录");
        }

        // 2. 获取专家用户ID
        String userIdStr = (String) authentication.getPrincipal();
        Long expertUserId;
        try {
            expertUserId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return ResultVO.fail( "用户ID格式错误");
        }

        // 3. 校验专家身份
        if (!isExpertUser(expertUserId)) {
            return ResultVO.fail("无权限操作，仅专家可发布内容");
        }

        try {
            // 4. 调用Service发布内容
            ExpertContentOperateVO vo = contentOperateService.publishContent(expertUserId, dto);
            return ResultVO.success("内容发布成功，待审核", vo);
        } catch (IllegalArgumentException e) {
            // 5. 处理参数校验异常
            return ResultVO.fail(e.getMessage());
        } catch (Exception e) {
            // 6. 处理系统异常
            return ResultVO.fail("内容发布失败：" + e.getMessage());
        }
    }

    /**
     * 编辑内容
     */
    @PutMapping("/{id}")
    public ResultVO<ExpertContentOperateVO> editContent(
            @PathVariable("id") Long contentId, // 路径参数：内容ID
            @RequestBody ExpertContentOperateDTO dto, // 可部分传参
            Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.fail("请先登录");
        }

        // 2. 获取专家用户ID
        String userIdStr = (String) authentication.getPrincipal();
        Long expertUserId;
        try {
            expertUserId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return ResultVO.fail("用户ID格式错误");
        }

        // 3. 校验专家身份
        if (!isExpertUser(expertUserId)) {
            return ResultVO.fail("无权限操作，仅专家可编辑内容");
        }

        try {
            // 4. 调用Service编辑内容
            ExpertContentOperateVO vo = contentOperateService.editContent(expertUserId, contentId, dto);
            return ResultVO.success("内容编辑成功，待审核", vo);
        } catch (IllegalArgumentException e) {
            // 5. 处理参数校验/权限异常
            return ResultVO.fail(e.getMessage());
        } catch (Exception e) {
            // 6. 处理系统异常
            return ResultVO.fail("内容编辑失败：" + e.getMessage());
        }
    }

    /**
     * 专家身份校验（复用之前的逻辑）
     */
    private boolean isExpertUser(Long userId) {
        Expert expert = expertMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Expert>()
                        .eq(Expert::getUserId, userId)
                        .eq(Expert::getStatus, "active")
        );
        return expert != null;
    }
}