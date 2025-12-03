package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyd.xs.dto.expert.VO.ResourceVO;
import com.cyd.xs.entity.User.Entity;
import com.cyd.xs.mapper.EntityMapper;
import com.cyd.xs.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends ServiceImpl<EntityMapper, Entity> implements ResourceService {

    @Override
    public Page<ResourceVO> getResourcePage(String keyword, String tag, String level, String sort, Integer pageNum, Integer pageSize) {
        // 处理默认值
        if (pageNum == null) pageNum = 1;
        if (pageSize == null) pageSize = 10;
        if (sort == null || sort.isEmpty()) sort = "hot"; // 默认热度排序

        // 计算分页偏移量
        int offset = (pageNum - 1) * pageSize;

        // 查询分页数据
        List<ResourceVO> resourceList = baseMapper.listResourceByPage(keyword, tag, level, sort, pageSize, offset);
        // 统计总数
        Integer total = baseMapper.countResourceTotal(keyword, tag, level);

        // 拼接资源详情链接（示例：/api/v1/expert/resource/15001）
        resourceList.forEach(vo -> {
            vo.setLink("/api/v1/expert/resource/" + vo.getId());
            // 收藏数简化为 0（实际需关联 collections 表统计：COUNT(*) FROM collections WHERE entity_type='resource' AND entity_id=vo.getId()）
            vo.setCollectCount(0);
        });

        // 封装分页结果
        Page<ResourceVO> page = new Page<>(pageNum, pageSize);
        page.setRecords(resourceList);
        page.setTotal(total);
        return page;
    }
}