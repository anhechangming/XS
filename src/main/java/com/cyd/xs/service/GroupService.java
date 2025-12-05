package com.cyd.xs.service;

import com.cyd.xs.dto.Group.*;
import org.springframework.transaction.annotation.Transactional;

public interface GroupService {


    GroupDTO getGroupList(String keyword, String tag, String sort, Integer pageNum, Integer pageSize, Long userId);

    @Transactional
    GroupCreateResultDTO createGroup(GroupCreateDTO request, Long userId);

    GroupDetailDTO getGroupDetail(Long groupId, Long userId);

    GroupJoinDTO joinOrQuitGroup(Long groupId, Long userId, String action);

    GroupDynamicResultDTO publishDynamic(Long groupId, GroupDynamicDTO request, Long userId);

    GroupResourceResultDTO uploadResource(Long groupId, GroupResourceDTO request, Long userId);
}