package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.dto.expert.DTO.EntityDTO;
import com.cyd.xs.dto.expert.DTO.ExpertInfoDTO;
import com.cyd.xs.dto.expert.VO.*;
import com.cyd.xs.mapper.*;
import com.cyd.xs.service.ExpertHomeService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertHomeServiceImpl implements ExpertHomeService {

    @Resource
    private ExpertMapper expertMapper;

    @Resource
    private EntityMapper entityMapper;

    @Resource
    private CollectionMapper collectionMapper;

    @Resource
    private FollowMapper followMapper;

    @Resource
    private ObjectMapper objectMapper;

    // 默认分页参数（匹配示例：pageNum=1，pageSize=5）
    private static final Integer DEFAULT_PAGE_NUM = 1;
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    @Override
    public ExpertHomeVO getExpertHome(Long expertId, Long userId, Integer pageNum, Integer pageSize) {
        // 1. 补充分页默认值
        if (pageNum == null || pageNum < 1) pageNum = DEFAULT_PAGE_NUM;
        if (pageSize == null || pageSize < 1 || pageSize > 20) pageSize = DEFAULT_PAGE_SIZE;

        // 2. 查询专家基础信息（关联用户表）
        ExpertInfoDTO expertInfoDTO = expertMapper.selectExpertInfoById(expertId);
        if (expertInfoDTO == null) {
            throw new RuntimeException("专家不存在或已下架");
        }
        Long expertUserId = expertInfoDTO.getUserId(); // 专家关联的用户ID（查内容用）

        // 3. 查询关注状态
        Boolean isFollowed = false;
        if (userId != null) {
            Integer followCount = followMapper.checkFollowStatus(userId, expertId);
            isFollowed = followCount > 0;
        }

        // 4. 组装专家信息VO
        ExpertInfoVO expertInfoVO = assembleExpertInfoVO(expertInfoDTO, isFollowed);

        // 5. 组装专家发布的内容（expertContent）
        PageVO<ExpertContentVO> contentPageVO = assembleContentPage(expertUserId, pageNum, pageSize);

        // 6. 组装专家答疑话题（expertQa）
        PageVO<ExpertQaVO> qaPageVO = assembleQaPage(expertUserId, pageNum, pageSize);

        // 7. 顶层VO组装
        ExpertHomeVO expertHomeVO = new ExpertHomeVO();
        expertHomeVO.setExpertInfo(expertInfoVO);
        expertHomeVO.setExpertContent(contentPageVO);
        expertHomeVO.setExpertQa(qaPageVO);

        return expertHomeVO;
    }

    // 组装专家基础信息
    private ExpertInfoVO assembleExpertInfoVO(ExpertInfoDTO dto, Boolean isFollowed) {
        ExpertInfoVO vo = new ExpertInfoVO();
        vo.setId(dto.getId());
        vo.setName(dto.getName() != null ? dto.getName() : "匿名专家");
        vo.setAvatar(dto.getAvatar() != null ? dto.getAvatar() : "https://picsum.photos/200/200");
        vo.setCertification(dto.getCertification() != null ? dto.getCertification() : "暂无认证");
        vo.setExpertise(dto.getExpertise() != null ? dto.getExpertise() : "暂无擅长领域");
        // BigDecimal转Double（保留1位小数）
        vo.setScore(dto.getScore() != null
                ? dto.getScore().setScale(1, RoundingMode.HALF_UP).doubleValue()
                : 0.0);
        vo.setConsultCount(dto.getConsultCount() != null ? dto.getConsultCount() : 0);
        vo.setIntro(dto.getIntro() != null ? dto.getIntro() : "暂无简介");
        vo.setFollowed(isFollowed);
        return vo;
    }

    // 组装专家发布的内容（expertContent）
    private PageVO<ExpertContentVO> assembleContentPage(Long expertUserId, Integer pageNum, Integer pageSize) {
        Page<EntityDTO> page = new Page<>(pageNum, pageSize);
        IPage<EntityDTO> entityIPage = entityMapper.selectResourceByAuthorId(page, expertUserId);

        // DTO转VO（严格匹配示例字段）
        List<ExpertContentVO> contentVOList = entityIPage.getRecords().stream()
                .map(this::assembleContentVO)
                .collect(Collectors.toList());

        // 分页VO组装
        PageVO<ExpertContentVO> pageVO = new PageVO<>();
        pageVO.setTotal(entityIPage.getTotal());
        pageVO.setPageNum(pageNum);
        pageVO.setPageSize(pageSize);
        pageVO.setList(contentVOList);
        return pageVO;
    }

    // 组装单个内容VO
    private ExpertContentVO assembleContentVO(EntityDTO dto) {
        ExpertContentVO vo = new ExpertContentVO();
        vo.setId(dto.getId());
        vo.setTitle(dto.getTitle());
        vo.setType(dto.getResourceType() != null ? dto.getResourceType() : "article"); // 兜底类型
        // 解析extra_json中的score
        vo.setScore(parseScoreFromExtraJson(dto.getExtraJson()));
        vo.setViewCount(dto.getHotValue() != null ? dto.getHotValue() : 0L);
        // 统计收藏数
        vo.setCollectCount(collectionMapper.countCollectionByEntityId(dto.getId()));
        vo.setPublishTime(dto.getCreatedAt());
        // 链接格式：/api/v1/expert/resource/{id}（匹配示例）
        vo.setLink("/api/v1/expert/resource/" + dto.getId());
        return vo;
    }

    // 从extra_json解析score
    private Double parseScoreFromExtraJson(String extraJson) {
        if (StringUtils.hasText(extraJson)) {
            try {
                JsonNode extraNode = objectMapper.readTree(extraJson);
                return extraNode.has("score") ? extraNode.get("score").asDouble() : 0.0;
            } catch (Exception e) {
                return 0.0;
            }
        }
        return 0.0;
    }

    // 组装专家答疑话题（expertQa）
    private PageVO<ExpertQaVO> assembleQaPage(Long expertUserId, Integer pageNum, Integer pageSize) {
        Page<EntityDTO> page = new Page<>(pageNum, pageSize);
        IPage<EntityDTO> entityIPage = entityMapper.selectQaByAuthorId(page, expertUserId);

        // DTO转VO（匹配示例字段）
        List<ExpertQaVO> qaVOList = entityIPage.getRecords().stream()
                .map(this::assembleQaVO)
                .collect(Collectors.toList());

        // 分页VO组装
        PageVO<ExpertQaVO> pageVO = new PageVO<>();
        pageVO.setTotal(entityIPage.getTotal());
        pageVO.setPageNum(pageNum);
        pageVO.setPageSize(pageSize);
        pageVO.setList(qaVOList);
        return pageVO;
    }

    // 组装单个答疑VO
    private ExpertQaVO assembleQaVO(EntityDTO dto) {
        ExpertQaVO vo = new ExpertQaVO();
        vo.setId(dto.getId());
        vo.setTitle(dto.getTitle());
        vo.setReplyCount(dto.getReplyCount() != null ? dto.getReplyCount() : 0); // 示例字段
        vo.setViewCount(dto.getHotValue() != null ? dto.getHotValue() : 0L);
        vo.setPublishTime(dto.getCreatedAt());
        // 链接格式：/api/v1/expert/qa/{id}（匹配示例）
        vo.setLink("/api/v1/expert/qa/" + dto.getId());
        return vo;
    }
}