package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchOutreachChannelPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewChannelDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenameChannelNameDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchOutreachChannelPageRO;
import com.norm.timemall.app.affiliate.domain.vo.FetchOutreachChannelPageVO;
import com.norm.timemall.app.affiliate.service.AffiliateOutreachChannelService;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.norm.timemall.app.affiliate.domain.dto.DelChannelDTO;

@RestController
public class OutreachController {
    @Autowired
    private AffiliateOutreachChannelService affiliateOutreachChannelService;
    @PutMapping("/api/v1/web/affiliate/rename_outreach_channel")
    public SuccessVO renameChannelName(@RequestBody RenameChannelNameDTO dto){
        affiliateOutreachChannelService.modifyChannelName(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/web/affiliate/del_outreach_channel")
    public SuccessVO delOutreachChannel(@RequestBody DelChannelDTO dto){
        affiliateOutreachChannelService.removeChannel(dto.getOutreachChannelId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/web/affiliate/new_outreach_channel")
    public SuccessVO newChannel(@RequestBody NewChannelDTO dto) {
        affiliateOutreachChannelService.addOneChannel(dto.getOutreachName());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/web/affiliate/outreach")
    public FetchOutreachChannelPageVO fetchOutreachChannel(FetchOutreachChannelPageDTO dto){
        IPage<FetchOutreachChannelPageRO> outreach=affiliateOutreachChannelService.findOutreachChannelRecord(dto);
        FetchOutreachChannelPageVO vo=new FetchOutreachChannelPageVO();
        vo.setOutreach(outreach);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
