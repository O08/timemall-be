package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.RecordNeedStoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface UserNeedStoryService {
    void addUserNeedStory(RecordNeedStoryDTO dto);
}
