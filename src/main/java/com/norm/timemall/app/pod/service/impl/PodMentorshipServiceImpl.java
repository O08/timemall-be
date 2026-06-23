package com.norm.timemall.app.pod.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.MenteeStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.Mentorship;
import com.norm.timemall.app.pod.domain.dto.PodFetchMentorPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodFetchMentorPageRO;
import com.norm.timemall.app.pod.mapper.PodBrandMapper;
import com.norm.timemall.app.pod.mapper.PodMentorshipMapper;
import com.norm.timemall.app.pod.service.PodMentorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class PodMentorshipServiceImpl implements PodMentorshipService {

    @Autowired
    private PodMentorshipMapper podMentorshipMapper;
    
    @Autowired
    private PodBrandMapper podBrandMapper;

    @Override
    public IPage<PodFetchMentorPageRO> findMentorPage(PodFetchMentorPageDTO dto) {
        String menteeBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        IPage<PodFetchMentorPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        
        return podMentorshipMapper.selectMentorPage(page, dto, menteeBrandId);
    }

    @Override
    public void deleteMentor(String id) {
        // Get the current user's brand ID to validate they are the mentee
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        // Check if the mentorship record exists
        Mentorship mentorship = podMentorshipMapper.selectById(id);
        if (mentorship == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        
        // Verify the current user is the mentee
        if (!currentBrandId.equals(mentorship.getMenteeBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        
        // Delete the mentorship record
        podMentorshipMapper.deleteById(id);
    }

    @Override
    public void applyMentor(String mentorBrandId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        String currentUserId = SecurityUserHelper.getCurrentPrincipal().getUserId();
        
        // 1. Check if mentor is applying to themselves
        if (currentBrandId.equals(mentorBrandId)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }


        // 2. Check if mentor brand exists
        Brand mentorBrand = podBrandMapper.selectById(mentorBrandId);
        if (mentorBrand == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        if(!isActiveMentor(mentorBrand.getModifiedAt())){
            throw new QuickMessageException("导师账号已六个月未活跃");
        }

        LambdaQueryWrapper<Mentorship> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(Mentorship::getMentorBrandId, mentorBrandId)
                .eq(Mentorship::getMenteeBrandId,currentBrandId);
        boolean alreadyApply = podMentorshipMapper.exists(wrapper);
        if(alreadyApply){
            throw new QuickMessageException("已申请过该导师");
        }

        // 3. Check if mentor already has 10 or more application status records
        int applicationCount = podMentorshipMapper.countMentorshipByMentorAndStatus(
            mentorBrandId, MenteeStatusEnum.APPLICATION.getCode());
        if (applicationCount > 10) {
            throw new QuickMessageException("导师预备席位已满");
        }
        
        // 4. Check if mentor already has 10 or more training status mentees
        int trainingCount = podMentorshipMapper.countMentorshipByMentorAndStatus(
            mentorBrandId, MenteeStatusEnum.TRAINING.getCode());
        if (trainingCount > 10) {
            throw new QuickMessageException("导师学员席位已满");
        }
        
        // 5. Create new mentorship record
        Mentorship mentorship = new Mentorship();
        mentorship.setId(IdUtil.simpleUUID())
                  .setMentorBrandId(mentorBrandId)
                  .setMenteeBrandId(currentBrandId)
                  .setMentorUserId(mentorBrand.getCustomerId())
                  .setMenteeUserId(currentUserId)
                  .setStatus(MenteeStatusEnum.APPLICATION.getCode())
                  .setGuidancePeriodEarning(BigDecimal.ZERO)
                  .setGuidancePeriodInfluencers(0L)
                  .setGuidancePeriodMessages(0L)
                  .setPastYearMessages(0L)
                  .setCreateAt(new Date())
                  .setModifiedAt(new Date());
        
        podMentorshipMapper.insert(mentorship);
    }

    private boolean isActiveMentor(Date lastLoginDate){
        if(lastLoginDate == null) return  false;
        // 获取当前时间并减去 6 个月
        Date sixMonthsAgo = DateUtil.offsetMonth(new Date(), -6);
        return lastLoginDate.after(sixMonthsAgo);
    }

}
