package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandMenteeInfoPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioManageMenteeStatusDTO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandMenteeInfoPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandOpenMenteeInfoVO;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;

@Service
public interface StudioMentorshipService {
    StudioFetchBrandOpenMenteeInfoVO findOpenMenteeInfo(String id);
    
    StudioFetchBrandMenteeInfoPageVO findBrandMenteeInfo(StudioFetchBrandMenteeInfoPageDTO dto);
    
    SuccessVO deleteMentee(String menteeRecordId);
    
    SuccessVO changeMenteeStatus(StudioManageMenteeStatusDTO dto);
}
