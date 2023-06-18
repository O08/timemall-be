package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsTemplateAllDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPutMpsTemplateDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplate;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplateDetailRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsTemplateDetailVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsTemplateVO;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import com.norm.timemall.app.studio.service.StudioMpsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioMpsTemplateController {
    @Autowired
    private StudioMpsTemplateService studioMpsTemplateService;
    @Autowired
    private StudioApiAccessControlService studioApiAccessControlService;

    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/mps_chain/template")
    public StudioFetchMpsTemplateVO fetchMpsTemplate(@RequestBody @Validated StudioFetchMpsTemplateAllDTO dto){

        StudioFetchMpsTemplate template = studioMpsTemplateService.findMpsTemplate(dto.getChainId());
        StudioFetchMpsTemplateVO vo = new StudioFetchMpsTemplateVO();
        vo.setTemplate(template);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @ResponseBody
    @GetMapping("/api/v1/web_estudio/mps_chain/template/{id}")
    public StudioFetchMpsTemplateDetailVO fetchTemplateDetail(@PathVariable("id") String id){

        StudioFetchMpsTemplateDetailRO detail = studioMpsTemplateService.findTemplateDetail(id);
        StudioFetchMpsTemplateDetailVO vo = new StudioFetchMpsTemplateDetailVO();
        vo.setDetail(detail);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }

    @ResponseBody
    @PostMapping("/api/v1/web_estudio/brand/mps_chain/new_template")
    public SuccessVO  newMpsTemplate(@RequestBody @Validated StudioNewMpsTemplateDTO dto){
        boolean isMpsChainFounder = studioApiAccessControlService.isMpsChainFounder(dto.getChainId());
        if(isMpsChainFounder){
            studioMpsTemplateService.newMpsTemplate(dto);
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @PutMapping("/api/v1/web_estudio/brand/mps_chain/new_template")
    public SuccessVO putMpsTemplate(@RequestBody @Validated StudioPutMpsTemplateDTO dto){

        studioMpsTemplateService.modifyMpsTemplate(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @DeleteMapping("/api/v1/web_estudio/brand/mps_template/{id}")
    public SuccessVO delTemplate(@PathVariable("id") String id){

        studioMpsTemplateService.delTemplate(id);

        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
