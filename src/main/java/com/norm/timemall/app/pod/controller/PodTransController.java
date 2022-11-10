package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.ro.PodTransRO;
import com.norm.timemall.app.pod.domain.vo.PodTransPageVO;
import com.norm.timemall.app.pod.service.PodOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 交易
 */
@RestController
public class PodTransController {

    @Autowired
    private PodOrderDetailService podOrderDetailService;

    /*
     * 分页查询用户订单
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_epod/me/transaction")
    public PodTransPageVO retrieveTrans(@Validated @RequestBody PageDTO transPageDTO, @AuthenticationPrincipal CustomizeUser user)
    {
        IPage<PodTransRO> trans = podOrderDetailService.findTrans(transPageDTO,user);
        PodTransPageVO transPageVO = new PodTransPageVO();
        transPageVO.setResponseCode(CodeEnum.SUCCESS);
        transPageVO.setTransactions(trans);
        return transPageVO;
    }
}
