package com.cyd.xs.controller.expert;

import com.cyd.xs.service.ExpertHomeService;
import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.expert.VO.ExpertHomeVO;
import com.cyd.xs.service.ExpertHomeService;

import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/expert")
public class ExpertHomeController {

    @Resource
    private ExpertHomeService expertHomeService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<ExpertHomeVO> getExpertHome(
            @PathVariable("id") Long id,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            Authentication authentication) {

        // 获取当前登录用户ID
        Long userId = null;
        if (authentication != null && authentication.isAuthenticated()) {
            userId = Long.parseLong(authentication.getName());
        }

        ExpertHomeVO expertHomeVO = expertHomeService.getExpertHome(id, userId, pageNum, pageSize);
        return ResultVO.success("获取成功", expertHomeVO);
    }
}