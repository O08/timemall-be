package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.DelPpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewPpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.PpcLinkPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenamePpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcLinkPageRO;
import com.norm.timemall.app.affiliate.domain.vo.PpcLinkPageVO;
import com.norm.timemall.app.affiliate.service.AffiliatePpcLinkService;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class PpcLinkController {

    @Autowired
    private AffiliatePpcLinkService affiliatePpcLinkService;

    @GetMapping("/api/v1/web/affiliate/ppc/link")
    public PpcLinkPageVO fetchPpcLinkPage(@Validated PpcLinkPageDTO dto){

        IPage<PpcLinkPageRO> link=affiliatePpcLinkService.findPpcLinkPage(dto);
        PpcLinkPageVO vo= new PpcLinkPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setLink(link);
        return vo;

    }
    @PostMapping("/api/v1/web/affiliate/ppc/link")
    public SuccessVO newPpcLink(@Validated @RequestBody NewPpcLinkDTO dto){

        affiliatePpcLinkService.addPpcLink(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web/affiliate/ppc/rename_link")
    public SuccessVO renameLink(@Validated @RequestBody RenamePpcLinkDTO dto){

        affiliatePpcLinkService.modifyLinkName(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/web/affiliate/ppc/link")
    public SuccessVO delPpcLink(@Validated @RequestBody DelPpcLinkDTO dto){

        affiliatePpcLinkService.removeLink(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }


}
