package com.cyd.xs.service.Impl;

import com.cyd.xs.dto.expert.DTO.ExpertContentOperateDTO;
import com.cyd.xs.dto.expert.VO.ExpertContentOperateVO;
import com.cyd.xs.entity.User.Entity;
import com.cyd.xs.mapper.ExpertContentOperateMapper;
import com.cyd.xs.service.ExpertContentOperateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExpertContentOperateServiceImpl implements ExpertContentOperateService {

    @Resource
    private ExpertContentOperateMapper contentOperateMapper;

    @Resource
    private ObjectMapper objectMapper;

    /**
     * 发布新内容（事务保证）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExpertContentOperateVO publishContent(Long expertUserId, ExpertContentOperateDTO dto) {
        // 1. 校验类型、标签、封面图合法性
        validateOperateParam(dto);

        // 2. 构建 Entity 对象
        Entity entity = buildEntityFromDTO(expertUserId, dto);
        entity.setStatus("pending"); // 发布后状态：待审核
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(LocalDateTime.now());
        entity.setHotValue(0L); // 初始热度0
        entity.setIsHot(0);     // 非热门
        entity.setIsTop(0);     // 非置顶

        // 3. 插入数据库
        contentOperateMapper.insert(entity);

        // 4. 组装返回结果
        ExpertContentOperateVO vo = new ExpertContentOperateVO();
        vo.setContentId(entity.getId());
        vo.setStatus("pending");
        vo.setSubmitTime(LocalDateTime.now());
        return vo;
    }

    /**
     * 编辑内容（事务保证）
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExpertContentOperateVO editContent(Long expertUserId, Long contentId, ExpertContentOperateDTO dto) {
        // 1. 校验内容是否属于当前专家
        int ownerCount = contentOperateMapper.checkContentOwner(contentId, expertUserId);
        if (ownerCount == 0) {
            throw new IllegalArgumentException("无权编辑该内容（内容不存在或不属于当前专家）");
        }

        // 2. 校验参数合法性（类型、标签、封面图）
        if (dto != null) {
            // 若传了type/tag，需校验；没传则不校验（保留原有值）
            if (dto.getType() != null && !dto.validateType()) {
                throw new IllegalArgumentException("内容类型不合法（仅支持video/article/ppt/audio）");
            }
            if (dto.getTag() != null && !dto.validateTag()) {
                throw new IllegalArgumentException("标签不合法（仅支持职业规划/简历优化/面试技巧/职场适应/心理咨询）");
            }
            if (dto.getType() != null && !dto.validateCoverImage()) {
                throw new IllegalArgumentException("视频/PPT/音频类型必须上传封面图");
            }
        }

        // 3. 构建更新实体（仅设置非空字段）
        Entity entity = new Entity();
        entity.setId(contentId);
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getType() != null) entity.setType(dto.getType());
      //  if (dto.getTag() != null) entity.setTag(dto.getTag()); // 若表有tag字段直接设，无则存入extraJson（需调整）
        if (dto.getContent() != null) entity.setContent(dto.getContent());
        if (dto.getCoverImage() != null) entity.setCoverImage(dto.getCoverImage());

//        // 4. 处理extraJson（仅传了desc时更新）
//        if (dto.getDesc() != null) {
//            try {
//                Map<String, Object> extraMap = new HashMap<>();
//                extraMap.put("desc", dto.getDesc());
//                // 若原有extraJson有其他字段（如score），可先查询再合并（可选）
//                entity.setExtraJson(objectMapper.writeValueAsString(extraMap));
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException("扩展字段序列化失败", e);
//            }
//        }

        // 4. 处理 extraJson（合并 tag + desc + 原有字段，核心修复）
        try {
            Map<String, Object> extraMap = new HashMap<>();
            // 先查询旧的 extraJson，避免覆盖原有字段（如 score、旧 tag/desc）
            Entity oldEntity = contentOperateMapper.selectById(contentId);
            if (oldEntity != null && oldEntity.getExtraJson() != null) {
                // 解析旧的扩展字段
                Map<String, Object> oldExtraMap = objectMapper.readValue(oldEntity.getExtraJson(), Map.class);
                extraMap.putAll(oldExtraMap); // 合并旧字段
            }
            // 用新传的参数覆盖（没传则保留旧值）
            if (dto.getTag() != null) extraMap.put("tag", dto.getTag());
            if (dto.getDesc() != null) extraMap.put("desc", dto.getDesc());
            // 设置到实体
            entity.setExtraJson(objectMapper.writeValueAsString(extraMap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("扩展字段序列化失败", e);
        }


        // 5. 执行更新
        int updateRows = contentOperateMapper.updateContentSelective(entity, expertUserId);
        if (updateRows == 0) {
            throw new RuntimeException("内容编辑失败");
        }

        // 6. 组装返回结果
        ExpertContentOperateVO vo = new ExpertContentOperateVO();
        vo.setContentId(contentId);
        vo.setStatus("pending");
        vo.setUpdateTime(LocalDateTime.now());
        return vo;
    }

    /**
     * 构建发布内容的Entity对象
     */
    private Entity buildEntityFromDTO(Long expertUserId, ExpertContentOperateDTO dto) {
        Entity entity = new Entity();
        entity.setAuthorId(expertUserId);
        entity.setTitle(dto.getTitle());
        entity.setType(dto.getType());
        entity.setContent(dto.getContent());
        entity.setCoverImage(dto.getCoverImage());

        // 处理extraJson（存入desc）
        try {
            Map<String, Object> extraMap = new HashMap<>();
            extraMap.put("desc", dto.getDesc());
            entity.setExtraJson(objectMapper.writeValueAsString(extraMap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("扩展字段序列化失败", e);
        }

        return entity;
    }

    /**
     * 校验发布参数合法性
     */
    private void validateOperateParam(ExpertContentOperateDTO dto) {
        if (!dto.validateType()) {
            throw new IllegalArgumentException("内容类型不合法（仅支持video/article/ppt/audio）");
        }
        if (!dto.validateTag()) {
            throw new IllegalArgumentException("标签不合法（仅支持职业规划/简历优化/面试技巧/职场适应/心理咨询）");
        }
        if (!dto.validateCoverImage()) {
            throw new IllegalArgumentException("视频/PPT/音频类型必须上传封面图");
        }
    }
}