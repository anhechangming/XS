package com.cyd.xs.controller.message;



import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.message.DTO.MessageBatchOperateDTO;
import com.cyd.xs.dto.message.VO.MessageDeleteVO;
import com.cyd.xs.dto.message.VO.MessageReadVO;
import com.cyd.xs.service.MessageOperateService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * 消息操作接口（标记已读/删除）
 */
@RestController
@RequestMapping("/api/v1/message")
@Validated
public class MessageOperateController {

    @Resource
    private MessageOperateService messageOperateService;

    /**
     * 批量标记消息为已读
     */
    @PutMapping(value = "/read", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<MessageReadVO> batchMarkRead(
            @Valid @RequestBody MessageBatchOperateDTO dto,
            Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.error(401, "请先登录");
        }

        // 2. 获取登录用户ID
        String userIdStr = (String) authentication.getPrincipal();
        Long userId = parseUserId(userIdStr);
        if (userId == null) {
            return ResultVO.error(400, "用户ID格式错误");
        }

        try {
            MessageReadVO vo = messageOperateService.batchMarkRead(userId, dto);
            return ResultVO.success("标记已读成功", vo);
        } catch (IllegalArgumentException e) {
            return ResultVO.error(400, e.getMessage());
        } catch (Exception e) {
            return ResultVO.error(500, "标记已读失败：" + e.getMessage());
        }
    }

    /**
     * 全部消息标记为已读
     */
    @PutMapping(value = "/read/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<MessageReadVO> markAllRead(Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.error(401, "请先登录");
        }

        // 2. 获取登录用户ID
        String userIdStr = (String) authentication.getPrincipal();
        Long userId = parseUserId(userIdStr);
        if (userId == null) {
            return ResultVO.error(400, "用户ID格式错误");
        }

        try {
            MessageReadVO vo = messageOperateService.markAllRead(userId);
            return ResultVO.success("全部消息标为已读成功", vo);
        } catch (Exception e) {
            return ResultVO.error(500, "全部消息标为已读失败：" + e.getMessage());
        }
    }

    /**
     * 批量删除消息
     */
    @DeleteMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<MessageDeleteVO> batchDelete(
            @Valid @RequestBody MessageBatchOperateDTO dto,
            Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.error(401, "请先登录");
        }

        // 2. 获取登录用户ID
        String userIdStr = (String) authentication.getPrincipal();
        Long userId = parseUserId(userIdStr);
        if (userId == null) {
            return ResultVO.error(400, "用户ID格式错误");
        }

        try {
            MessageDeleteVO vo = messageOperateService.batchDelete(userId, dto);
            return ResultVO.success("消息删除成功", vo);
        } catch (IllegalArgumentException e) {
            return ResultVO.error(400, e.getMessage());
        } catch (Exception e) {
            return ResultVO.error(500, "消息删除失败：" + e.getMessage());
        }
    }

    /**
     * 工具方法：解析用户ID（String → Long）
     */
    private Long parseUserId(String userIdStr) {
        try {
            return Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}