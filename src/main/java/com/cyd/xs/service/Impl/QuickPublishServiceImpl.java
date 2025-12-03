package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.content.DTO.QuickPublishDTO;
import com.cyd.xs.dto.content.VO.QuickPublishVO;
import com.cyd.xs.entity.User.Entity;
import com.cyd.xs.mapper.QuickPublishMapper;
import com.cyd.xs.service.QuickPublishService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class QuickPublishServiceImpl implements QuickPublishService {

    @Resource
    private QuickPublishMapper publishMapper;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 发布内容（事务保证）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public QuickPublishVO publish(Long userId, QuickPublishDTO dto) {
        // 1. 差异化参数校验（按内容类型）
        dto.validateByType();

        // 2. 构建 Entity 对象
        Entity entity = buildEntityFromDTO(userId, dto);

        // 3. 插入数据库
        publishMapper.insert(entity);

        // 4. 组装返回结果
        QuickPublishVO vo = new QuickPublishVO();
        vo.setContentId(entity.getId());
        vo.setType(dto.getType());
        vo.setStatus("pending");
        vo.setSubmitTime(LocalDateTime.now());
        return vo;
    }

    /**
     * 构建 Entity 对象（字段映射核心逻辑）
     */
    private Entity buildEntityFromDTO(Long userId, QuickPublishDTO dto) {
        Entity entity = new Entity();

        // 基础字段赋值
        entity.setAuthorId(userId);
        entity.setType(dto.getType());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setStatus("pending"); // 待审核状态
        entity.setHotValue(0L);      // 初始热度0
        entity.setIsHot(0);          // 非热门
        entity.setIsTop(0);          // 非置顶
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());

        // 小组ID（仅post类型可能有）
      //  entity.setGroupId(dto.getGroupId()); // 若表无groupId字段，注释此行，后续存入extraJson

        // 封面图：用第一张图片作为封面（可选）
        if (dto.getImageUrls() != null && !dto.getImageUrls().isEmpty()) {
            entity.setCoverImage(dto.getImageUrls().get(0));
        }

        // 扩展字段：存储tags和imageUrls（核心）
        try {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("tags", dto.getTags());       // 标签列表
            extraMap.put("imageUrls", dto.getImageUrls()); // 图片URL列表

            // 若表无groupId字段，将groupId存入extraJson
             if (dto.getGroupId() != null) {
                 extraMap.put("groupId", dto.getGroupId());
             }

            entity.setExtraJson(objectMapper.writeValueAsString(extraMap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("扩展字段序列化失败", e);
        }

        return entity;
    }
}