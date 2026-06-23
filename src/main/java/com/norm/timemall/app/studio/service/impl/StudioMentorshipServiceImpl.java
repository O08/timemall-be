package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Mentorship;
import com.norm.timemall.app.studio.domain.dto.StudioFetchBrandMenteeInfoPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioManageMenteeStatusDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandMenteeInfoPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandMenteeInfoPageVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandOpenMenteeInfoRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchBrandOpenMenteeInfoVO;
import com.norm.timemall.app.base.enums.MenteeStatusEnum;
import com.norm.timemall.app.studio.mapper.StudioMentorshipMapper;
import com.norm.timemall.app.studio.service.StudioMentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class StudioMentorshipServiceImpl implements StudioMentorshipService {

    @Autowired
    private StudioMentorshipMapper studioMentorshipMapper;
    
    @Override
    public StudioFetchBrandOpenMenteeInfoVO findOpenMenteeInfo(String id) {

        ArrayList<StudioFetchBrandOpenMenteeInfoRO> menteeInfoList = studioMentorshipMapper.findOpenMenteeInfo(id);

        StudioFetchBrandOpenMenteeInfoVO vo = new StudioFetchBrandOpenMenteeInfoVO();
        vo.setMentee(menteeInfoList);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    
    @Override
    public StudioFetchBrandMenteeInfoPageVO findBrandMenteeInfo(StudioFetchBrandMenteeInfoPageDTO dto) {
        Page<StudioFetchBrandMenteeInfoPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        
        // Get the mentor brand ID from the current user context
        String mentorBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        IPage<StudioFetchBrandMenteeInfoPageRO> menteeInfoPage = studioMentorshipMapper.selectMenteePage(page, dto, mentorBrandId);
        
        StudioFetchBrandMenteeInfoPageVO vo = new StudioFetchBrandMenteeInfoPageVO();
        vo.setMentee(menteeInfoPage);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    
    @Override
    public SuccessVO deleteMentee(String menteeRecordId) {
        // Get the current user's brand ID to validate they are the mentor
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        // Check if the current user is the mentor in the mentorship relationship
        Mentorship mentorship = studioMentorshipMapper.selectById(menteeRecordId);
        if (mentorship == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        
        if (!currentBrandId.equals(mentorship.getMentorBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        
        // Delete the mentorship record
        studioMentorshipMapper.deleteById(menteeRecordId);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    
    @Override
    public SuccessVO changeMenteeStatus(StudioManageMenteeStatusDTO dto) {
        // Get the current user's brand ID to validate they are the mentor
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        // Check if the mentorship record exists and get current status
        Mentorship mentorship = studioMentorshipMapper.selectById(dto.getId());
        if (mentorship == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        
        // Verify the current user is the mentor
        if (!currentBrandId.equals(mentorship.getMentorBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        
        // Validate status transition
        String currentStatus = mentorship.getStatus();
        String newStatus = dto.getStatus();
        
        if (!isValidTransitionForChangeStatus(currentStatus, newStatus)) {
            throw new QuickMessageException(
                String.format("非法状态变更： 从 %s 到 %s",
                    getStatusDescription(currentStatus), getStatusDescription(newStatus)));
        }
        
        // Update the status
        mentorship.setStatus(newStatus);
        mentorship.setModifiedAt(new Date());

        studioMentorshipMapper.updateById(mentorship);
        
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    public  boolean isValidTransitionForChangeStatus(String fromStatus, String toStatus) {
        if (fromStatus == null || toStatus == null) {
            return false;
        }

        // Graduated status cannot be changed
        if (MenteeStatusEnum.GRADUATED.getCode().equals(fromStatus)) {
            return false;
        }

        // Application can only transition to Training
        if (MenteeStatusEnum.APPLICATION.getCode().equals(fromStatus)) {
            return MenteeStatusEnum.TRAINING.getCode().equals(toStatus);
        }

        // Training can only transition to Graduated
        if (MenteeStatusEnum.TRAINING.getCode().equals(fromStatus)) {
            return MenteeStatusEnum.GRADUATED.getCode().equals(toStatus);
        }

        return false;
    }
    
    private String getStatusDescription(String statusCode) {
        MenteeStatusEnum status = MenteeStatusEnum.fromCode(statusCode);
        return status != null ? status.getDescription() : "Unknown Status";
    }
}
