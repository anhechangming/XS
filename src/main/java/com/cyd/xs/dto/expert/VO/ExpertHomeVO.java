package com.cyd.xs.dto.expert.VO;

import lombok.Data;

@Data
public class ExpertHomeVO {
    private ExpertInfoVO expertInfo;       // 专家基础信息
    private PageVO<ExpertContentVO> expertContent; // 专家发布的内容
    private PageVO<ExpertQaVO> expertQa;   // 专家答疑话题（恢复原始字段）

    public ExpertInfoVO getExpertInfo() {
        return expertInfo;
    }

    public void setExpertInfo(ExpertInfoVO expertInfo) {
        this.expertInfo = expertInfo;
    }

    public PageVO<ExpertContentVO> getExpertContent() {
        return expertContent;
    }

    public void setExpertContent(PageVO<ExpertContentVO> expertContent) {
        this.expertContent = expertContent;
    }

    public PageVO<ExpertQaVO> getExpertQa() {
        return expertQa;
    }

    public void setExpertQa(PageVO<ExpertQaVO> expertQa) {
        this.expertQa = expertQa;
    }
}
