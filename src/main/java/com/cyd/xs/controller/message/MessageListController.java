package com.cyd.xs.controller.message;


import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.message.DTO.MessageListQueryDTO;
import com.cyd.xs.dto.message.VO.MessageListVO;
import com.cyd.xs.service.MessageListService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息列表接口
 */
@RestController
@RequestMapping("/api/v1/message")
public class MessageListController {

    @Resource
    private MessageListService messageListService;

    /**
     * 获取消息列表（支持筛选+分页）
     */
    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<MessageListVO> getMessageList(
            MessageListQueryDTO queryDTO, // Query参数自动绑定
            Authentication authentication) {

        // 1. 校验登录状态
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.error(401, "请先登录");
        }

        // 2. 获取登录用户ID（消息接收者ID）
        String userIdStr = (String) authentication.getPrincipal();
        Long userId;
        try {
            userId = Long.parseLong(userIdStr);
        } catch (NumberFormatException e) {
            return ResultVO.error(400, "用户ID格式错误");
        }

        try {
            // 3. 调用Service查询消息列表
            MessageListVO messageListVO = messageListService.getMessageList(userId, queryDTO);
            return ResultVO.success("获取成功", messageListVO);
        } catch (Exception e) {
            // 4. 处理系统异常
            return ResultVO.error(500, "获取消息列表失败：" + e.getMessage());
        }
    }
}