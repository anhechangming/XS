package com.cyd.xs.controller.expert;

import com.cyd.xs.Utils.ResultUtil;
import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.expert.DTO.ExpertApplyDTO;
import com.cyd.xs.dto.expert.VO.ExpertApplyVO;
import com.cyd.xs.service.ExpertApplyService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// 方式二：使用Java内置日志
import java.util.logging.Logger;
import java.security.Principal;
@Slf4j
@RestController
@RequestMapping("/api/v1/expert")
public class ExpertApplyController {

    @Resource
    private ExpertApplyService expertApplyService;

    @PostMapping(value="/apply",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<ExpertApplyVO> submitApply(
            Principal principal, // Spring 自动注入认证后的用户信息
            @Valid @RequestBody ExpertApplyDTO dto
    ) {
        // 直接获取用户ID（Principal.getName() 就是 JWT 解析出的 userId）
        Long userId = Long.parseLong(principal.getName());
       // log.info("用户 " + userId + " 提交专家入驻申请");

        ExpertApplyVO applyVO = expertApplyService.submitApply(userId, dto);
        return ResultUtil.success(
                "入驻申请提交成功，2个工作日内审核",
                applyVO
        );
    }
}