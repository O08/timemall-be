package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CoopApplicationStatusEnum;
import com.norm.timemall.app.base.enums.CoopProgramStatusEnum;
import com.norm.timemall.app.base.enums.ElectricityBusinessTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CoopApplications;
import com.norm.timemall.app.base.mo.CoopPrograms;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.pod.domain.dto.*;
import com.norm.timemall.app.pod.domain.ro.PodCoopProgramApplicationRO;
import com.norm.timemall.app.pod.domain.ro.PodDiscoveryProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchCoopBrandApplicationsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodPostedProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodProgramInfoRO;
import com.norm.timemall.app.pod.mapper.PodCoopApplicationsMapper;
import com.norm.timemall.app.pod.mapper.PodCoopProgramsMapper;
import com.norm.timemall.app.pod.service.PodCoopProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class PodCoopProgramServiceImpl implements PodCoopProgramService {
    @Autowired
    private PodCoopProgramsMapper podCoopProgramsMapper;

    @Autowired
    private PodCoopApplicationsMapper podCoopApplicationsMapper;

    @Autowired
    private BaseElectricityService baseElectricityService;

    private static final int COOP_PROGRAM_CREATE_COST = 10;




    @Override
    public IPage<PodDiscoveryProgramsPageRO> findDiscoveryProgramsPage(PodDiscoveryProgramsPageDTO dto) {
        Page<PodDiscoveryProgramsPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return podCoopProgramsMapper.selectDiscoveryProgramsPage(page, dto, currentBrandId);
    }

    @Override
    public IPage<PodPostedProgramsPageRO> findPostedProgramsPage(PodPostedProgramsPageDTO dto) {
        Page<PodPostedProgramsPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return podCoopProgramsMapper.selectPostedProgramsPage(page, dto, currentBrandId);
    }

    @Override
    public void invalidateProgram(String programId) {
        CoopPrograms program = podCoopProgramsMapper.selectById(programId);
        if (program == null) {
            throw new QuickMessageException("未找到相关合作项目，操作失败");
        }


        if (CoopProgramStatusEnum.INVALId.getValue().equals(program.getStatus())) {
            throw new QuickMessageException("该项目已是无效状态，无需重复操作");
        }

        // 需要发布7天后才能标记为无效
        LocalDateTime createdAt = program.getCreatedAt().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (Duration.between(createdAt, LocalDateTime.now()).toDays() < 7) {
            throw new QuickMessageException("发布未满7天，无法标记为无效");
        }

        LambdaUpdateWrapper<CoopPrograms> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopPrograms::getId, programId)
                .set(CoopPrograms::getStatus, CoopProgramStatusEnum.INVALId.getValue())
                .set(CoopPrograms::getModifiedAt, new Date());
        podCoopProgramsMapper.update(updateWrapper);
    }

    @Override
    public void warmUpProgram(String programId) {
        CoopPrograms program = podCoopProgramsMapper.selectById(programId);
        if (program == null) {
            throw new QuickMessageException("未找到相关合作项目");
        }

        int currentBuzz = program.getBuzz() == null ? 0 : program.getBuzz();
        if (currentBuzz >= 9999) {
            throw new QuickMessageException("热度已达上限，无法继续加热");
        }

        LambdaUpdateWrapper<CoopPrograms> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopPrograms::getId, programId)
                .set(CoopPrograms::getBuzz, currentBuzz + 1)
                .set(CoopPrograms::getModifiedAt, new Date());
        podCoopProgramsMapper.update(updateWrapper);
    }

    @Override
    public void editProgram(PodEditProgramDTO dto) {
        CoopPrograms program = podCoopProgramsMapper.selectById(dto.getProgramId());
        if (program == null) {
            throw new QuickMessageException("未找到相关合作项目");
        }

        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentBrandId.equals(program.getAuthorBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        LambdaUpdateWrapper<CoopPrograms> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopPrograms::getId, dto.getProgramId())
                .set(CoopPrograms::getTitle, dto.getTitle())
                .set(CoopPrograms::getDescription, dto.getDescription())
                .set(CoopPrograms::getOnlineLink, dto.getOnlineLink())
                .set(CoopPrograms::getWorkMode, dto.getWorkMode())
                .set(CoopPrograms::getTopics, JSONUtil.toJsonStr(dto.getTopics()))
                .set(CoopPrograms::getStatus, dto.getStatus())
                .set(CoopPrograms::getModifiedAt, new Date());
        podCoopProgramsMapper.update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProgram(PodCreateProgramDTO dto, String thumbnailUrl) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Date now = new Date();
        String id=IdUtil.simpleUUID();

        // deduct electricity
        baseElectricityService.deduct(currentBrandId,COOP_PROGRAM_CREATE_COST,"发布合作信息扣除源能", ElectricityBusinessTypeEnum.DEDUCT.getMark(), id, "项目："+id);


        CoopPrograms program = new CoopPrograms();
        program.setId(id)
                .setAuthorBrandId(currentBrandId)
                .setTitle(dto.getTitle())
                .setThumbnail(thumbnailUrl)
                .setDescription(dto.getDescription())
                .setOnlineLink(dto.getOnlineLink())
                .setWorkMode(dto.getWorkMode())
                .setTopics(dto.getTopics())
                .setBuzz(0)
                .setApplys(0)
                .setAttendees(0)
                .setStatus(CoopProgramStatusEnum.RECRUITING.getValue())
                .setCreatedAt(now)
                .setModifiedAt(now);
        podCoopProgramsMapper.insert(program);



    }

    @Override
    public void changeProgramThumbnail(String programId, String thumbnailUrl) {
        LambdaUpdateWrapper<CoopPrograms> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopPrograms::getId, programId)
                .set(CoopPrograms::getThumbnail, thumbnailUrl)
                .set(CoopPrograms::getModifiedAt, new Date());
        podCoopProgramsMapper.update(updateWrapper);
    }

    @Override
    public CoopPrograms findProgramById(String programId) {
        return podCoopProgramsMapper.selectById(programId);
    }

    @Override
    public void deleteProgram(String programId) {
        // 删除关联的 application 数据
        LambdaQueryWrapper<CoopApplications> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CoopApplications::getProgramId, programId);
        podCoopApplicationsMapper.delete(queryWrapper);

        // 删除 program
        podCoopProgramsMapper.deleteById(programId);
    }

    @Override
    public IPage<PodCoopProgramApplicationRO> findProgramApplicationsPage(PodFetchCoopProgramApplicationsPageDTO dto) {
        Page<PodCoopProgramApplicationRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return podCoopProgramsMapper.selectProgramApplicationsPage(page, dto);
    }

    @Override
    public void applyForProgram(PodCoopApplyForProgramDTO dto) {
        String programId = dto.getProgramId();
        CoopPrograms program = podCoopProgramsMapper.selectById(programId);
        if (program == null) {
            throw new QuickMessageException("未找到相关合作项目");
        }
        if (!CoopProgramStatusEnum.RECRUITING.getValue().equals(program.getStatus())) {
            throw new QuickMessageException("该项目当前不在招募中");
        }

        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (currentBrandId.equals(program.getAuthorBrandId())) {
            throw new QuickMessageException("不能申请自己发布的项目");
        }

        // 检查是否已申请过
        LambdaQueryWrapper<CoopApplications> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(CoopApplications::getProgramId, programId)
                .eq(CoopApplications::getApplicantBrandId, currentBrandId);
        if (podCoopApplicationsMapper.selectCount(queryWrapper) > 0) {
            throw new QuickMessageException("您已申请过该项目，请勿重复申请");
        }

        Date now = new Date();
        CoopApplications application = new CoopApplications();
        application.setId(IdUtil.simpleUUID())
                .setProgramId(programId)
                .setApplicantBrandId(currentBrandId)
                .setMessage(dto.getMessage())
                .setStatus(CoopApplicationStatusEnum.PENDING.getValue())
                .setCreatedAt(now)
                .setModifiedAt(now);
        podCoopApplicationsMapper.insert(application);

        // 增加 program 申请数
        LambdaUpdateWrapper<CoopPrograms> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopPrograms::getId, programId)
                .setSql("applys = applys + 1")
                .set(CoopPrograms::getModifiedAt, now);
        podCoopProgramsMapper.update(updateWrapper);
    }

    @Override
    public void deleteApplication(String applicationId) {
        CoopApplications application = podCoopApplicationsMapper.selectById(applicationId);
        if (application == null) {
            throw new QuickMessageException("未找到相关申请记录");
        }
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
         if (!currentBrandId.equals(application.getApplicantBrandId())) {
            throw new QuickMessageException("无权删除该申请");
        }

        podCoopApplicationsMapper.deleteById(applicationId);

        // 减少 program 申请数；如果申请已通过，同时减少 attendees
        LambdaUpdateWrapper<CoopPrograms> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopPrograms::getId, application.getProgramId())
                .setSql("applys = CASE WHEN applys > 0 THEN applys - 1 ELSE 0 END")
                .set(CoopPrograms::getModifiedAt, new Date());
        if (CoopApplicationStatusEnum.APPROVED.getValue().equals(application.getStatus())) {
            updateWrapper.setSql("attendees = CASE WHEN attendees > 0 THEN attendees - 1 ELSE 0 END");
        }
        podCoopProgramsMapper.update(updateWrapper);
    }

    @Override
    public void auditApplication(PodAuditCoopProgramApplicationDTO dto) {
        if(CoopApplicationStatusEnum.PENDING==dto.getStatus()){
            throw new QuickMessageException("审批状态不正确");
        }
        CoopApplications application = podCoopApplicationsMapper.selectById(dto.getApplicationId());
        if (application == null) {
            throw new QuickMessageException("未找到相关申请记录");
        }

        // 只能审批 pending 状态的申请
        if (!CoopApplicationStatusEnum.PENDING.getValue().equals(application.getStatus())) {
            throw new QuickMessageException("该申请已被处理，无法重复审批");
        }

        // 只有 program 发布者才能审批
        CoopPrograms program = podCoopProgramsMapper.selectById(application.getProgramId());
        if (program == null) {
            throw new QuickMessageException("未找到关联的合作项目");
        }
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentBrandId.equals(program.getAuthorBrandId())) {
            throw new QuickMessageException("无权审批该申请");
        }

        LambdaUpdateWrapper<CoopApplications> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(CoopApplications::getId, dto.getApplicationId())
                .set(CoopApplications::getStatus, dto.getStatus().getValue())
                .set(CoopApplications::getModifiedAt, new Date());
        podCoopApplicationsMapper.update(updateWrapper);

        // 审批通过时，递增 program 的 attendees
        if (CoopApplicationStatusEnum.APPROVED == dto.getStatus()) {
            LambdaUpdateWrapper<CoopPrograms> programUpdateWrapper = Wrappers.lambdaUpdate();
            programUpdateWrapper.eq(CoopPrograms::getId, program.getId())
                    .setSql("attendees = attendees + 1")
                    .set(CoopPrograms::getModifiedAt, new Date());
            podCoopProgramsMapper.update(programUpdateWrapper);
        }
    }

    @Override
    public IPage<PodFetchCoopBrandApplicationsPageRO> findBrandApplicationsPage(PodFetchCoopBrandApplicationsPageDTO dto) {
        Page<PodFetchCoopBrandApplicationsPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return podCoopProgramsMapper.selectBrandApplicationsPage(page, dto, currentBrandId);
    }

    @Override
    public PodProgramInfoRO getProgramInfo(String programId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return podCoopProgramsMapper.selectProgramInfo(programId, currentBrandId);
    }
}
