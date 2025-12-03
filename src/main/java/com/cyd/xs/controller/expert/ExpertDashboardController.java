package com.cyd.xs.controller.expert;

import com.cyd.xs.Utils.ResultVO;
import com.cyd.xs.dto.expert.DTO.ExpertContentQueryDTO;
import com.cyd.xs.dto.expert.VO.ExpertContentPageVO;
import com.cyd.xs.dto.expert.VO.ExpertDashboardVO;
import com.cyd.xs.entity.Expert.Expert;
import com.cyd.xs.mapper.ExpertMapper;


import com.cyd.xs.service.ExpertContentService;
import com.cyd.xs.service.ExpertContentService;
import com.cyd.xs.service.ExpertDashboardService;
import jakarta.annotation.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * 专家后台数据面板接口
 */
@RestController
@RequestMapping("/api/v1/expert/backend")
public class ExpertDashboardController {

    @Resource
    private ExpertDashboardService dashboardService;

    @Resource
    private ExpertContentService contentService;

    @Resource
    private ExpertMapper expertMapper;

    /**
     * 获取专家数据面板（仅专家身份可访问）
     */
    @GetMapping(value = "/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<ExpertDashboardVO> getDashboard(Authentication authentication) {
        // 1. 校验用户是否登录
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResultVO.fail("请先登录");
        }

        // 2. 获取当前登录用户ID和权限（从JWT Token解析）
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userIdStr = (String) authentication.getPrincipal();
//        Long userId = Long.parseLong(userDetails.getUsername()); // 假设Token中存储的是用户ID
        Long userId = Long.parseLong(userIdStr);

        // 3. 校验是否为专家身份（两种方式，选一种即可）
        // 方式一：通过角色权限校验（推荐，若登录时已分配 ROLE_EXPERT 角色）
//        boolean isExpert = hasExpertRole(userDetails.getAuthorities());
        // 方式二：通过专家表关联校验（若未用角色权限）
         boolean isExpert = isExpertUser(userId);

        if (!isExpert) {
            return ResultVO.fail( "无权限访问，仅专家可查看");
        }

        // 4. 调用Service获取统计数据
        ExpertDashboardVO dashboardVO = dashboardService.getExpertDashboard(userId);

        // 5. 组装返回结果
        return ResultVO.success("获取成功", dashboardVO);
    }

    /**
     * 校验是否有专家角色（ROLE_EXPERT）
     */
    private boolean hasExpertRole(Collection<? extends GrantedAuthority> authorities) {
        for (GrantedAuthority authority : authorities) {
            if ("ROLE_EXPERT".equals(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 备选：通过专家表校验是否为专家（若未使用角色权限）
     */
    private boolean isExpertUser(Long userId) {
        Expert expert = expertMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Expert>()
                        .eq(Expert::getUserId, userId)
                        .eq(Expert::getStatus, "active")
        );
        return expert != null;
    }


    @GetMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultVO<ExpertContentPageVO> getContentList(
            ExpertContentQueryDTO queryDTO,
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
            return ResultVO.fail("无权限访问，仅专家可查看");
        }

        // 4. 调用Service查询
        ExpertContentPageVO contentPageVO = contentService.getExpertContentList(expertUserId, queryDTO);

        // 5. 返回结果
        return ResultVO.success("获取成功", contentPageVO);
    }




}