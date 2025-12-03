package com.cyd.xs.service.Impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyd.xs.dto.expert.DTO.ExpertContentQueryDTO;
import com.cyd.xs.dto.expert.VO.ExpertContentItemVO;
import com.cyd.xs.dto.expert.VO.ExpertContentPageVO;
import com.cyd.xs.entity.User.Entity;
import com.cyd.xs.mapper.ExpertContentMapper;
import com.cyd.xs.service.ExpertContentService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpertContentServiceImpl implements ExpertContentService {

    @Resource
    private ExpertContentMapper contentMapper;

    @Resource
    private ObjectMapper objectMapper;

    @Override
    public ExpertContentPageVO getExpertContentList(Long expertUserId, ExpertContentQueryDTO queryDTO) {
        // 1. 校验参数（status默认设为passed）
        queryDTO.validate();

        // 2. 构建分页对象
        Page<Entity> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        // 打印查询条件（排查参数是否正确）
        System.out.println("查询条件：expertUserId=" + expertUserId + ", type=" + queryDTO.getType() + ", status=" + queryDTO.getStatus());
        // 3. 分页查询（status仅匹配passed）
        IPage<Entity> entityIPage = contentMapper.selectExpertContentPage(page, expertUserId, queryDTO);
        // 打印查询结果（确认是否有数据）
        System.out.println("查询到的总记录数：" + entityIPage.getTotal());
        System.out.println("查询到的记录：" + entityIPage.getRecords());
        // 4. Entity 转 VO（status自动设为passed）
        List<ExpertContentItemVO> itemVOList = entityIPage.getRecords().stream()
                .map(this::convertToItemVO)
                .collect(Collectors.toList());

        // 5. 组装分页结果
        ExpertContentPageVO pageVO = new ExpertContentPageVO();
        pageVO.setTotal(entityIPage.getTotal());
        pageVO.setPageNum(queryDTO.getPageNum());
        pageVO.setPageSize(queryDTO.getPageSize());
        pageVO.setList(itemVOList);

        return pageVO;
    }

    /**
     * Entity 转 VO（status无需映射，VO默认已设为passed）
     */
    private ExpertContentItemVO convertToItemVO(Entity entity) {
        ExpertContentItemVO itemVO = new ExpertContentItemVO();

        // 基础字段映射
        itemVO.setId(entity.getId());
        itemVO.setTitle(entity.getTitle());
        itemVO.setType(entity.getType());
        itemVO.setViewCount(entity.getHotValue() != null ? entity.getHotValue() : 0L);
        itemVO.setPublishTime(entity.getCreatedAt());

        // 解析评分
        itemVO.setScore(parseScoreFromExtraJson(entity.getExtraJson()));

        // 统计收藏数
        itemVO.setCollectCount(contentMapper.countCollectByEntityId(entity.getId()));

        return itemVO;
    }

    /**
     * 从extra_json解析评分
     */
    private Double parseScoreFromExtraJson(String extraJson) {
        if (!StringUtils.hasText(extraJson)) {
            return 0.0;
        }
        try {
            JsonNode extraNode = objectMapper.readTree(extraJson);
            if (extraNode.has("score")) {
                BigDecimal scoreBig = new BigDecimal(extraNode.get("score").asText());
                return scoreBig.setScale(1, RoundingMode.HALF_UP).doubleValue();
            }
        } catch (Exception e) {
            // 解析失败返回0.0
        }
        return 0.0;
    }
}