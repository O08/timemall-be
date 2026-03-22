package com.norm.timemall.app.studio.controller;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommercialPaperTagEnum;
import com.norm.timemall.app.base.enums.MpsTagEnum;
import com.norm.timemall.app.base.enums.MpsTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Mps;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewFastMpsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioTaggingMpsDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsListRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsListPageVO;
import com.norm.timemall.app.studio.service.StudioCommercialPaperService;
import com.norm.timemall.app.studio.service.StudioMpsChainService;
import com.norm.timemall.app.studio.service.StudioMpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioMpsController {
    @Autowired
    private StudioMpsService studioMpsService;
    @Autowired
    private StudioCommercialPaperService studioCommercialPaperService;
    @Autowired
    private StudioMpsChainService studioMpsChainService;

    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/mps")
    public StudioFetchMpsListPageVO fetchMpsList(StudioFetchMpsListPageDTO dto){
        IPage<StudioFetchMpsListRO> mps= studioMpsService.fetchMpsList(dto);
        StudioFetchMpsListPageVO vo = new StudioFetchMpsListPageVO();
        vo.setMps(mps);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/brand/mps/new")
    public SuccessVO newMps(@RequestBody @Validated StudioNewMpsDTO dto){
        // new mps
        Mps mps = studioMpsService.newMps(dto);
        // generate mps paper
        studioCommercialPaperService.generateMpsPaper(mps.getChainId(),mps.getBrandId(), mps.getId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping(value = "/api/v1/web_estudio/brand/mps/new_fast_paper")
    public SuccessVO newFastMpsPaper(@RequestBody @Validated StudioNewFastMpsDTO dto){
        // new mps
        Mps mps = studioMpsService.newFastMps(dto);
        // generate mps paper
        studioCommercialPaperService.generateFastMpsPaper(dto,mps.getBrandId(), mps.getId());

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/mps/tag")
    public SuccessVO taggingMps(@RequestBody @Validated StudioTaggingMpsDTO dto){

        Mps mps = studioMpsService.findMps(dto.getMpsId());
        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        if(mps==null || !brandId.equals(mps.getBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(MpsTypeEnum.FROM_TEMPLATE.getMark().equals(mps.getMpsType()) ){
            doTaggingMps(dto,mps);
        }
        if(MpsTypeEnum.FAST.getMark().equals(mps.getMpsType()) ){
            doTaggingFastMps(dto,mps);
        }


        return new SuccessVO(CodeEnum.SUCCESS);

    }
    private void doTaggingMps(StudioTaggingMpsDTO dto,Mps mps){
        if(CharSequenceUtil.isBlank(dto.getChainId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean allow=false;
        // if tag to  PUBLISH, need to check current tag must be CREATED  if pass then tagging mps paper and update chain statistics
        if(MpsTagEnum.PUBLISH.getMark().equals(dto.getTag())&&
                MpsTagEnum.CREATED.getMark().equals(mps.getTag())){
            studioCommercialPaperService.modifyPapersTag(dto.getMpsId(), CommercialPaperTagEnum.PUBLISH.getMark());
            studioMpsChainService.modifyMpsChainProcessingCnt(dto.getChainId());
            allow=true;
        }
        // if tag to OFFLINE, need to check mps current tag must be CREATED ,  if pass then tagging mps paper
        if(MpsTagEnum.OFFLINE.getMark().equals(dto.getTag())
                && MpsTagEnum.CREATED.getMark().equals(mps.getTag())){
            studioCommercialPaperService.modifyPapersTag(dto.getMpsId(),CommercialPaperTagEnum.OFFLINE.getMark());
            allow=true;
        }
        // if tag to END,need to check mps current tag must be 'PUBLISH' and update chain statistics
        if(MpsTagEnum.END.getMark().equals(dto.getTag())
                && MpsTagEnum.PUBLISH.getMark().equals(mps.getTag())){
            studioMpsChainService.modifyMpsChainProcessedCnt(dto.getChainId());
            allow=true;
        }
        if(allow){
            studioMpsService.taggingMps(dto);
        }
        if(!allow){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
    }
    private void doTaggingFastMps(StudioTaggingMpsDTO dto, Mps mps){

        boolean canUpdatePaperTagAsPublish=MpsTagEnum.PUBLISH.getMark().equals(dto.getTag()) &&
                MpsTagEnum.CREATED.getMark().equals(mps.getTag());

        boolean canUpdatePaperTagAsOffline=MpsTagEnum.OFFLINE.getMark().equals(dto.getTag())
                && MpsTagEnum.CREATED.getMark().equals(mps.getTag());

        boolean needUpdatePaperTag=canUpdatePaperTagAsPublish || canUpdatePaperTagAsOffline;


        if(needUpdatePaperTag){

            studioCommercialPaperService.modifyPapersTag(dto.getMpsId(), dto.getTag());

        }
        boolean needUpdateMps=needUpdatePaperTag || MpsTagEnum.END.getMark().equals(dto.getTag());

        if(needUpdateMps){
            studioMpsService.taggingMps(dto);
        }

    }

}
