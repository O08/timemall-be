package com.norm.timemall.app.indicator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;

import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.indicator.domain.dto.CellIndicesPageDTO;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerCellIndicesDTO;
import com.norm.timemall.app.indicator.domain.dto.IndDataLayerVirtualProductIndicesDTO;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import com.norm.timemall.app.indicator.domain.vo.IndCellIndicesPageVO;
import com.norm.timemall.app.indicator.service.CellIndicesService;
import com.norm.timemall.app.indicator.service.IndAffiliateAccessService;
import com.norm.timemall.app.indicator.service.IndVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class CellIndicesController {

    @Autowired
    private CellIndicesService cellIndicesService;
    @Autowired
    private IndAffiliateAccessService indAffiliateAccessService;

    @Autowired
    private IndVirtualProductService indVirtualProductService;
    /*
     * 分页查询服务指标： 曝光、点击。。。。
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/cellIndices")
    public IndCellIndicesPageVO retrieveCellIndices(@Validated CellIndicesPageDTO dto, @AuthenticationPrincipal CustomizeUser user){
        IPage<IndCellIndicesRO> cells = cellIndicesService.findCellIndices(dto,user);
        IndCellIndicesPageVO vo  = new IndCellIndicesPageVO();
        vo.setCells(cells)
                .setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }


    @ResponseBody
    @PutMapping("/api/v1/data_layer/cell/indices")
    public SuccessVO dataLayerCellIndices(@Validated @RequestBody IndDataLayerCellIndicesDTO dto,
                                          HttpServletRequest request){

        cellIndicesService.modifyCellIndices(dto);
        indAffiliateAccessService.newAccess(dto,request);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_mall/data_layer/virtual_product/indices")
    public SuccessVO dataLayerVirtualProductIndices(@Validated @RequestBody IndDataLayerVirtualProductIndicesDTO dto){
        indVirtualProductService.modifyIndices(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }


}
