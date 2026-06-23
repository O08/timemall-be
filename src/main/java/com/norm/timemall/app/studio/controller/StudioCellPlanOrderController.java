package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CellPlanOrderTagEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.dto.FetchCellPlanOrderPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioTagCellPlanOrderDTO;
import com.norm.timemall.app.studio.domain.ro.StudioCellPlanOrderPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCellPlanOrderRO;
import com.norm.timemall.app.studio.domain.vo.StudioCellPlanOrderPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchCellPlanOrderVO;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import com.norm.timemall.app.studio.service.StudioCellPlanOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioCellPlanOrderController {
    @Autowired
    private StudioCellPlanOrderService studioCellPlanOrderService;
    @Autowired
    private StudioApiAccessControlService studioApiAccessControlService;
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/cell/plan_order")
    public StudioCellPlanOrderPageVO retrieveCellPlanOrders(@Validated FetchCellPlanOrderPageDTO dto){

        IPage<StudioCellPlanOrderPageRO> planOrder=studioCellPlanOrderService.findCellPlanOrderPage(dto);
        StudioCellPlanOrderPageVO vo = new StudioCellPlanOrderPageVO();
        vo.setPlanOrder(planOrder);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/cell/plan_order/{id}")
    public StudioFetchCellPlanOrderVO fetchCellPlanOrder(@PathVariable("id") String id){

        StudioFetchCellPlanOrderRO ro = studioCellPlanOrderService.findCellPlanOrder(id);
        StudioFetchCellPlanOrderVO vo =new StudioFetchCellPlanOrderVO();
        vo.setOrder(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/cell/plan_order/{id}/tag")
    public SuccessVO tagCellPlanOrder(@PathVariable("id")String id, @RequestBody @Validated  StudioTagCellPlanOrderDTO dto){

        // only support tag to delivering
        boolean checked = studioApiAccessControlService.isCellPlanOrderSupplierAndCheckOrderTag(id,CellPlanOrderTagEnum.PAID.ordinal()+"");
        if(!checked || !(""+CellPlanOrderTagEnum.DELIVERING.ordinal()).equals(dto.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        studioCellPlanOrderService.modifyCellPlanOrderTag(id,dto.getTag());
        return new SuccessVO(CodeEnum.SUCCESS);

    }


}
