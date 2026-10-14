package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.lang.Validator;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.OasisFastLink;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.TeamAddNewFastLinkDTO;
import com.norm.timemall.app.team.domain.ro.FetchFastLinkRO;
import com.norm.timemall.app.team.domain.vo.FetchFastLinkVO;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisFastLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
public class TeamOasisFastLinkController {
    @Autowired
    private TeamOasisFastLinkService fastLinkService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;


    @GetMapping(value = "/api/v1/oasis/{id}/fast_link")
    public FetchFastLinkVO FetchFastLinks(@PathVariable("id") String id){

        ArrayList<FetchFastLinkRO> link = fastLinkService.findFastLinks(id);
        FetchFastLinkVO vo = new FetchFastLinkVO();
        vo.setLink(link);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @PostMapping("/api/v1/oasis/fast_link/new")
    public SuccessVO addNewFastLink(@Validated TeamAddNewFastLinkDTO dto) throws IOException {

        if(!Validator.isUrl(dto.getLinkUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
         // validate file
        if(dto.getLogo() == null || dto.getLogo().isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(dto.getLogo().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validate role
        // check oasis ,if role is oasis creator ,pass
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validate max-limit 8
        boolean canAdd =  fastLinkService.validateLinkMaxLimit(dto.getOasisId());
        if(!canAdd){
            throw new ErrorCodeException(CodeEnum.MAX_LIMIT);
        }

        String logoUrl=fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(dto.getLogo(), FileStoreDir.FAST_LINK_LOGO);

        fastLinkService.addOneFastLink(dto,logoUrl);

        return  new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/oasis/fast_link/{id}/del")
    public SuccessVO deleteOneFastLink(@PathVariable("id") String id){
        // validate role is admin for oasis
        OasisFastLink fastLink =  fastLinkService.findOneFastLink(id);
        if(fastLink == null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // validate role
        // check oasis ,if role is oasis creator ,pass
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(fastLink.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // remove record
        fastLinkService.removeOneFastLink(id);
        // remove file
        fileStoreService.deleteImageAndAvifFile(fastLink.getLogo());


        return  new SuccessVO(CodeEnum.SUCCESS);


    }

}
