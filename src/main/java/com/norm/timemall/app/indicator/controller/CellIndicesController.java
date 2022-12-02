package com.norm.timemall.app.indicator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import com.norm.timemall.app.indicator.domain.vo.IndCellIndicesPageVO;
import com.norm.timemall.app.indicator.service.CellIndicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CellIndicesController {

    @Autowired
    private CellIndicesService cellIndicesService;
    /*
     * 分页查询服务指标： 曝光、点击。。。。
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/cellIndices")
    public IndCellIndicesPageVO retrieveCellIndices(@Validated PageDTO PageDTO, @AuthenticationPrincipal CustomizeUser user){
        IPage<IndCellIndicesRO> cells = cellIndicesService.findCellIndices(PageDTO,user);
        IndCellIndicesPageVO vo  = new IndCellIndicesPageVO();
        vo.setCells(cells)
                .setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
