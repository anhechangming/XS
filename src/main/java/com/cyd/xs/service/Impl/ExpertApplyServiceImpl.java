package com.cyd.xs.service.Impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyd.xs.dto.expert.DTO.ExpertApplyDTO;
import com.cyd.xs.dto.expert.VO.ExpertApplyVO;
import com.cyd.xs.entity.Expert.Expert;
import com.cyd.xs.entity.Expert.ExpertApply;
import com.cyd.xs.mapper.ExpertApplyMapper;
import com.cyd.xs.mapper.ExpertMapper;
import com.cyd.xs.service.ExpertApplyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ExpertApplyServiceImpl implements ExpertApplyService {

    @Resource
    private ExpertApplyMapper expertApplyMapper;

    @Resource
    private ExpertMapper expertMapper;

    @Resource
    private ObjectMapper objectMapper; // Jackson，用于JSON序列化
    // 注入 Spring 内置的 JSON 转换器（自动配置，无需手动创建）
    @Resource
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    // 时间格式化器（返回给前端的时间格式）
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ExpertApplyVO submitApply(Long userId, ExpertApplyDTO dto) {
        // 1. 校验：该用户是否已成为专家（避免重复申请）
        LambdaQueryWrapper<Expert> expertQuery = new LambdaQueryWrapper<Expert>()
                .eq(Expert::getUserId, userId)
                .eq(Expert::getStatus, "active");
        long expertCount = expertMapper.selectCount(expertQuery);
        if (expertCount > 0) {
            throw new RuntimeException("您已成为专家，无需重复申请");
        }

        // 2. 校验：该用户是否有未审核的申请（避免重复提交）
        LambdaQueryWrapper<ExpertApply> applyQuery = new LambdaQueryWrapper<ExpertApply>()
                .eq(ExpertApply::getUserId, userId)
                .eq(ExpertApply::getStatus, "pending");
        long applyCount = expertApplyMapper.selectCount(applyQuery);
        if (applyCount > 0) {
            throw new RuntimeException("您已有未审核的入驻申请，请耐心等待");
        }

        // 3. 构建申请实体（DTO转Entity）
        ExpertApply apply = new ExpertApply();
        BeanUtils.copyProperties(dto, apply);

        // 4. 补充必要字段
        apply.setUserId(userId); // 关联登录用户ID
        apply.setStatus("pending"); // 申请状态：待审核
        apply.setSubmitTime(LocalDateTime.now()); // 提交时间

        // 5. 从业经历（List<Map> 转 JSON 字符串存储）
        try {
            List<Map<String, Object>> workExperienceList = dto.getWorkExperience();
            // 校验每个从业经历对象的必填字段（可选，但推荐，避免前端传空数据）
            for (int i = 0; i < workExperienceList.size(); i++) {
                Map<String, Object> exp = workExperienceList.get(i);
                // 示例：校验公司名称、职位、开始时间必填
                if (exp.get("companyName") == null || exp.get("companyName").toString().trim().isEmpty()) {
                    throw new IllegalArgumentException("第" + (i + 1) + "条从业经历的公司名称不能为空");
                }
                if (exp.get("position") == null || exp.get("position").toString().trim().isEmpty()) {
                    throw new IllegalArgumentException("第" + (i + 1) + "条从业经历的职位不能为空");
                }
                if (exp.get("startTime") == null || exp.get("startTime").toString().trim().isEmpty()) {
                    throw new IllegalArgumentException("第" + (i + 1) + "条从业经历的开始时间不能为空");
                }
            }

            // 序列化 List<Map> 为 JSON 字符串
            String workExpJson = objectMapper.writeValueAsString(workExperienceList);
            apply.setWorkExperience(workExpJson);
           // log.info("用户{}从业经历序列化成功：{}", userId, workExpJson);
        } catch (IllegalArgumentException e) {
            // 字段校验失败
           // log.error("用户{}从业经历字段校验失败：{}", userId, e.getMessage());
            throw new RuntimeException(e.getMessage());
        } catch (JsonProcessingException e) {
            // 序列化失败（极少发生，除非前端传了非法JSON格式）
          //  log.error("用户{}从业经历格式转换失败！原始数据：{}，异常原因：{}",
           //         userId, dto.getWorkExperience(), e.getMessage(), e);
            throw new RuntimeException("从业经历格式不正确，请检查是否为有效的JSON数组");
        }

        // 6. 入库
        expertApplyMapper.insert(apply);

        // 7. 构建返回VO
        ExpertApplyVO vo = new ExpertApplyVO();
        vo.setApplyId(apply.getId());
        vo.setStatus(apply.getStatus());
        vo.setSubmitTime(apply.getSubmitTime().format(DATE_FORMATTER));

        return vo;
    }
}