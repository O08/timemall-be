package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
import com.norm.timemall.app.studio.domain.vo.StudioTransPageVO;
import com.norm.timemall.app.studio.service.StudioOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioTransController {

    @Autowired
    private StudioOrderDetailsService studioOrderDetailsService;

    /**
     *
     * 商家交易
     * @param brandId
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/{brand_id}/transaction")
    public StudioTransPageVO retrievTrans(@PathVariable("brand_id") String brandId,
                                          @AuthenticationPrincipal CustomizeUser user,
                                          @Validated @RequestBody PageDTO dto)
    {
        IPage<StudioTransRO> trans = studioOrderDetailsService.findTrans(brandId,user.getUserId(),dto);
        StudioTransPageVO vo = new StudioTransPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setTransactions(trans);
        return vo;
    }
}
