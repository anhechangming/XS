package com.cyd.xs.controller.expert;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.Response.Result;
import com.cyd.xs.dto.expert.VO.ExpertVO;
import com.cyd.xs.dto.expert.VO.ResourceVO;
import com.cyd.xs.service.ExpertService;
import com.cyd.xs.service.ResourceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/expert")
public class ExpertCenterController {

    @Resource
    private ResourceService resourceService;

    @Resource
    private ExpertService expertService;

    /**
     * 1. 获取专业资源列表（带筛选、排序、分页）
     * 请求示例：GET /api/v1/expert/resource/list?keyword=简历&tag=简历优化&level=S&sort=hot&pageNum=1&pageSize=10
     */
    @GetMapping("/resource/list")
    public Result<Page<ResourceVO>> getResourceList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize
    ) {
        Page<ResourceVO> resourcePage = resourceService.getResourcePage(keyword, tag, level, sort, pageNum, pageSize);
        return Result.success("获取专业资源列表成功", resourcePage);
    }

    /**
     * 2. 获取专家列表（带筛选、排序、分页）
     * 请求示例：GET /api/v1/expert/list?keyword=互联网&tag=职业规划&sort=score&pageNum=1&pageSize=10
     */
    @GetMapping("/list")
    public Result<Page<ExpertVO>> getExpertList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize
    ) {
        Page<ExpertVO> expertPage = expertService.getExpertPage(keyword, tag, sort, pageNum, pageSize);
        return Result.success("获取专家列表成功", expertPage);
    }
}