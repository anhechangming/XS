package com.cyd.xs.controller.expert;

import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.expert.DTO.ExpertMessageDTO;
import com.cyd.xs.dto.expert.VO.MessageResultVO;
import com.cyd.xs.service.ExpertMessageService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 专家私信咨询接口
 */
@RestController
@RequestMapping("/api/v1/expert")
@Validated // 开启参数校验
public class ExpertMessageController {

    @Resource
    private ExpertMessageService messageService;

    /**
     * 发送咨询消息给专家
     * @param id 专家ID（路径参数）
     * @param dto 咨询消息参数（已做校验）
     * @param authentication 登录认证信息（获取当前用户ID）
     * @return 发送结果
     */
    @PostMapping(value = "/{id}/message", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<MessageResultVO> sendMessage(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExpertMessageDTO dto, // @Valid 触发参数校验
            Authentication authentication) {

        // 1. 校验用户是否登录
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.fail("请先登录后再咨询");
        }

        // 2. 获取当前登录用户ID（从JWT Token解析）
        Long userId = Long.parseLong(authentication.getName());

        // 3. 调用Service发送消息
        MessageResultVO resultVO = messageService.sendExpertMessage(id, dto, userId);

        // 4. 组装返回结果
        return ResultVO.success("咨询消息发送成功", resultVO);
    }
}