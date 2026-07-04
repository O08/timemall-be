package com.norm.timemall.app.pod.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CoopProgramStatusEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CoopPrograms;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.pod.domain.dto.*;
import com.norm.timemall.app.pod.domain.ro.PodCoopProgramApplicationRO;
import com.norm.timemall.app.pod.domain.ro.PodDiscoveryProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodFetchCoopBrandApplicationsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodPostedProgramsPageRO;
import com.norm.timemall.app.pod.domain.ro.PodProgramInfoRO;
import com.norm.timemall.app.pod.domain.vo.PodDiscoveryProgramsPageVO;
import com.norm.timemall.app.pod.domain.vo.PodFetchCoopBrandApplicationsPageVO;
import com.norm.timemall.app.pod.domain.vo.PodFetchCoopProgramApplicationsPageVO;
import com.norm.timemall.app.pod.domain.vo.PodPostedProgramsPageVO;
import com.norm.timemall.app.pod.domain.vo.PodCoopProgramInfoVO;
import com.norm.timemall.app.pod.service.PodCoopProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class PodCoopProgramController {

    @Autowired
    private PodCoopProgramService podCoopProgramService;

    @Autowired
    private FileStoreService fileStoreService;



    @GetMapping("/api/v1/web_pod/discovery/cooperation/programs")
    public PodDiscoveryProgramsPageVO discoveryCoopPrograms(@Validated PodDiscoveryProgramsPageDTO dto) {
        IPage<PodDiscoveryProgramsPageRO> program = podCoopProgramService.findDiscoveryProgramsPage(dto);
        PodDiscoveryProgramsPageVO vo = new PodDiscoveryProgramsPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setProgram(program);
        return vo;
    }

    @GetMapping("/api/v1/web_pod/cooperation/program/{id}/info")
    public PodCoopProgramInfoVO getProgramInfo(@PathVariable("id") String programId) {
        PodProgramInfoRO program = podCoopProgramService.getProgramInfo(programId);
        PodCoopProgramInfoVO vo = new PodCoopProgramInfoVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setProgram(program);
        return vo;
    }


    @GetMapping("/api/v1/web_pod/cooperation/posted/programs")
    public PodPostedProgramsPageVO findUserPostedPrograms(@Validated PodPostedProgramsPageDTO dto) {
        IPage<PodPostedProgramsPageRO> program = podCoopProgramService.findPostedProgramsPage(dto);
        PodPostedProgramsPageVO vo = new PodPostedProgramsPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setProgram(program);
        return vo;
    }

    @GetMapping("/api/v1/web_pod/cooperation/program/applications")
    public PodFetchCoopProgramApplicationsPageVO findProgramApplicationsPage(@Validated PodFetchCoopProgramApplicationsPageDTO dto) {
        IPage<PodCoopProgramApplicationRO> application = podCoopProgramService.findProgramApplicationsPage(dto);
        PodFetchCoopProgramApplicationsPageVO vo = new PodFetchCoopProgramApplicationsPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setApplication(application);
        return vo;
    }
    @GetMapping("/api/v1/web_pod/cooperation/brand/applications")
    public PodFetchCoopBrandApplicationsPageVO findBrandApplicationsPage(@Validated PodFetchCoopBrandApplicationsPageDTO dto) {
        IPage<PodFetchCoopBrandApplicationsPageRO> application = podCoopProgramService.findBrandApplicationsPage(dto);
        PodFetchCoopBrandApplicationsPageVO vo = new PodFetchCoopBrandApplicationsPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setApplication(application);
        return vo;
    }


    @PostMapping("/api/v1/web_pod/cooperation/program/apply_for")
    public SuccessVO applyForProgram(@Validated @RequestBody PodCoopApplyForProgramDTO dto) {
        podCoopProgramService.applyForProgram(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/web_pod/cooperation/application/{id}/del")
    public SuccessVO deleteApplication(@PathVariable("id") String applicationId) {
        podCoopProgramService.deleteApplication(applicationId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_pod/cooperation/program/application/audit")
    public SuccessVO auditProgramApplication(@Validated @RequestBody PodAuditCoopProgramApplicationDTO dto) {
        podCoopProgramService.auditApplication(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


    @PostMapping("/api/v1/web_pod/cooperation/program/new")
    public SuccessVO createNewProgram(@Validated PodCreateProgramDTO dto) throws IOException {
        // validate link url
        if(ObjectUtil.isNotEmpty(dto.getOnlineLink()) && !Validator.isUrl(dto.getOnlineLink())){
            throw new QuickMessageException("链接格式不正确");
        }

        MultipartFile thumbnail = dto.getThumbnail();
        if (thumbnail == null || thumbnail.isEmpty()) {
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        String thumbnailUrl = fileStoreService.storeWithUnlimitedAccess(dto.getThumbnail(), FileStoreDir.COOP_PROGRAM_THUMBNAIL);

        podCoopProgramService.createProgram(dto,thumbnailUrl);


        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_pod/cooperation/program/edit")
    public SuccessVO editProgram(@Validated @RequestBody PodEditProgramDTO dto) {
        // validate link url
        if(ObjectUtil.isNotEmpty(dto.getOnlineLink()) && !Validator.isUrl(dto.getOnlineLink())){
            throw new QuickMessageException("链接格式不正确");
        }
        podCoopProgramService.editProgram(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_pod/cooperation/program/thumbnail/change")
    public SuccessVO changeProgramThumbnail(@Validated PodChangeProgramThumbnailDTO dto) throws IOException {
        MultipartFile thumbnail = dto.getThumbnail();
        if (thumbnail == null || thumbnail.isEmpty()) {
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        String fileType = FileTypeUtil.getType(thumbnail.getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e -> e.equals(fileType));
        if (notInExtensions) {
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        // query program: check existence + ownership + get old thumbnail
        CoopPrograms program = podCoopProgramService.findProgramById(dto.getProgramId());
        if (program == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (CoopProgramStatusEnum.FREEZE.getValue().equals(program.getStatus())){
            throw new QuickMessageException("项目已冻结");
        }
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentBrandId.equals(program.getAuthorBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        String oldThumbnail = program.getThumbnail();

        // store new thumbnail
        String thumbnailUrl = fileStoreService.storeWithUnlimitedAccess(thumbnail, FileStoreDir.COOP_PROGRAM_THUMBNAIL);

        // update program thumbnail
        podCoopProgramService.changeProgramThumbnail(dto.getProgramId(), thumbnailUrl);

        // delete old thumbnail file
        if (oldThumbnail != null && !oldThumbnail.isBlank()) {
            fileStoreService.deleteFile(oldThumbnail);
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/web_pod/cooperation/program/{id}/del")
    public SuccessVO deleteProgram(@PathVariable("id") String programId) {
        CoopPrograms program = podCoopProgramService.findProgramById(programId);
        if (program == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if (CoopProgramStatusEnum.FREEZE.getValue().equals(program.getStatus())){
            throw new QuickMessageException("项目已冻结");
        }
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentBrandId.equals(program.getAuthorBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        String thumbnail = program.getThumbnail();

        podCoopProgramService.deleteProgram(programId);

        // delete thumbnail file
        if (thumbnail != null && !thumbnail.isBlank()) {
            fileStoreService.deleteFile(thumbnail);
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }




    @PutMapping("/api/v1/web_pod/cooperation/program/{id}/mark_as_invalid")
    public SuccessVO markProgramAsInvalid(@PathVariable("id") String programId) {
        podCoopProgramService.invalidateProgram(programId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


    @PutMapping("/api/v1/web_pod/cooperation/program/{id}/warm_up")
    public SuccessVO warmupProgram(@PathVariable("id") String programId) {
        podCoopProgramService.warmUpProgram(programId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
