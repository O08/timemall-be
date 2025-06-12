package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.AppDeskElement;
import com.norm.timemall.app.base.mo.AppDeskTopic;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.TeamAppDeskGetElementsVO;
import com.norm.timemall.app.team.service.TeamAppDeskService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppDeskController {
    @Autowired
    private TeamAppDeskService teamAppDeskService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping("/api/v1/app/desk/yourapps")
    public TeamAppDeskGetElementsVO getElements(@Validated TeamAppDeskGetElementsDTO dto){
       return  teamAppDeskService.findElements(dto);
    }
    @PostMapping("/api/v1/app/desk/topic/new")
    public SuccessVO createNewTopic(@RequestBody @Validated TeamAppDeskNewTopicDTO dto){
        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChn());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        teamAppDeskService.addOneTopic(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/app/desk/topic/edit")
    public SuccessVO editTopic(@RequestBody @Validated TeamAppDeskEditTopicDTO dto){

        AppDeskTopic topic = teamAppDeskService.findOneTopic(dto.getTopicId());
        if(topic==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(topic.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        teamAppDeskService.editTopic(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/app/desk/topic/{id}/del")
    public SuccessVO delTopic(@PathVariable("id") String topicId){

        AppDeskTopic topic = teamAppDeskService.findOneTopic(topicId);
        if(topic==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(topic.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // del topic from db
        teamAppDeskService.delOneTopic(topicId);

        // del element from db
        teamAppDeskService.delElementsOwnedByTopic(topicId);

        // reorder topic
        teamAppDeskService.reorderTopicWhenDel(topic.getOasisChannelId(),topic.getOd());

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/app/desk/topic/reorder")
    public SuccessVO reorderTopic(@Validated @RequestBody TeamAppDeskReorderTopicDTO dto){

        AppDeskTopic topic = teamAppDeskService.findOneTopic(dto.getTopicId());
        if(topic==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(topic.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        teamAppDeskService.reorderTopic(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PostMapping("/api/v1/app/desk/element/new")
    public SuccessVO newElement(@Validated  AppDeskNewElementDTO dto) throws IOException {
        // validate link url
        if(!Validator.isUrl(dto.getLinkUrl())){
            throw new QuickMessageException("link url  not valid");
        }

        if(dto.getIconFile() == null || dto.getIconFile().isEmpty()){
            throw new QuickMessageException("icon file is empty");
        }

        String fileType= FileTypeUtil.getType(dto.getIconFile().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new QuickMessageException("icon file format not support, enum format:  png,jpeg,jpg,gif");
        }

        AppDeskTopic topic = teamAppDeskService.findOneTopic(dto.getTopicId());
        if(topic==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }


        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(topic.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        String iconUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getIconFile(), FileStoreDir.APP_DESK_ELEMENT_ICON);

        teamAppDeskService.addOneElement(dto,iconUrl);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/app/desk/element/{id}/del")
    public SuccessVO delElement(@PathVariable("id") String id){

        AppDeskElement appDeskElement = teamAppDeskService.findOneElement(id);
        if(appDeskElement==null){
            throw new QuickMessageException("target element not exist");
        }
        AppDeskTopic topic = teamAppDeskService.findOneTopic(appDeskElement.getAppDeskTopicId());

        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(topic.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // del from db
        teamAppDeskService.delOneElement(id);
        // del icon img from oss
        fileStoreService.deleteImageAndAvifFile(appDeskElement.getIconUrl());

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/app/desk/element/edit")
    public SuccessVO editElement(@Validated @RequestBody AppDeskEditElementDTO dto){

        // validate link url
        if(!Validator.isUrl(dto.getLinkUrl())){
            throw new QuickMessageException("link url  not valid");
        }
        AppDeskElement appDeskElement = teamAppDeskService.findOneElement(dto.getId());
        if(appDeskElement==null){
            throw new QuickMessageException("target element not exist");
        }

        AppDeskTopic topic = teamAppDeskService.findOneTopic(appDeskElement.getAppDeskTopicId());
        if(topic==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only admin can execute  operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(topic.getOasisChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }


        teamAppDeskService.changeElement(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/app/desk/element/{id}/data_science")
    public SuccessVO captureElementData(@PathVariable("id") String id){

        teamAppDeskService.storeElementStatisticsData(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
