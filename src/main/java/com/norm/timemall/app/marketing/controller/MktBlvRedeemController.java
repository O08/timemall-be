package com.norm.timemall.app.marketing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.marketing.domain.dto.MktClaimRedeemDTO;
import com.norm.timemall.app.marketing.domain.ro.MktFetchRedeemHistoryPageRO;
import com.norm.timemall.app.marketing.domain.vo.MktFetchRedeemHistoryPageVO;
import com.norm.timemall.app.marketing.service.MktBlvRedeemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MktBlvRedeemController {
    @Autowired
    private MktBlvRedeemService mktBlvRedeemService;
    @GetMapping(value = "/api/v1/marketing/redeem/history")
    public MktFetchRedeemHistoryPageVO getRedeemHistory(@Validated PageDTO dto){
        IPage<MktFetchRedeemHistoryPageRO> page = mktBlvRedeemService.findRedeemHistory(dto);
        MktFetchRedeemHistoryPageVO vo = new MktFetchRedeemHistoryPageVO();
        vo.setRedeemHistory(page);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping(value = "/api/v1/marketing/redeem/claim")
    public SuccessVO claim(@RequestBody @Validated MktClaimRedeemDTO dto){
        mktBlvRedeemService.claim(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
