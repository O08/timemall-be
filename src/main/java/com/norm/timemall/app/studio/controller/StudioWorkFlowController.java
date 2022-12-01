package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.domain.dto.StudioWorkflowPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioTransRO;
import com.norm.timemall.app.studio.domain.vo.StudioTransPageVO;
import com.norm.timemall.app.studio.service.StudioOrderDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioWorkFlowController {
    @Autowired
    private StudioOrderDetailsService studioOrderDetailsService;

    /**
     *
     * 商家工作流查询
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/millstone/workflow")
    public StudioTransPageVO retrievWrokflows(
            @AuthenticationPrincipal CustomizeUser user,
            @Validated StudioWorkflowPageDTO dto)
    {
        IPage<StudioTransRO> trans = studioOrderDetailsService.findWorkflowForBrand(user.getUserId(),dto);
        StudioTransPageVO vo = new StudioTransPageVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setTransactions(trans);
        return vo;
    }
}
