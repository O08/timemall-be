package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.studio.domain.ro.StudioCellRO;
import com.norm.timemall.app.studio.domain.vo.StudioCellInitVO;
import com.norm.timemall.app.studio.domain.vo.StudioCellPageVO;
import com.norm.timemall.app.studio.service.StudioCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioCellController {
    @Autowired
    private StudioCellService studioCellService;

    @Autowired
    private DataPolicyService dataPolicyService;
    /*
     * 分页查询归属当前商家多个服务
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/{brand_id}/cells")
    public StudioCellPageVO retrieveCells(@PathVariable("brand_id") String brandId, @Validated PageDTO cellPageDTO)
    {
        IPage<StudioCellRO> cells = studioCellService.findCells(brandId,cellPageDTO);
        StudioCellPageVO cellPageVO = new StudioCellPageVO();
        cellPageVO.setCells(cells)
                .setResponseCode(CodeEnum.SUCCESS);
        return cellPageVO;
    }

    /**
     * 修改服务状态
     * @param cellId
     * @param code
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/services/{cell_id}/mark")
    public SuccessVO markCell(@PathVariable("cell_id") String cellId,@RequestParam("code") String code)
    {
        // todo code 检查
        // todo cell id 归属检查
        studioCellService.markCell(cellId,code);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/services/initialize")
    public StudioCellInitVO initCell(@RequestParam("brandId") String brandId)
    {
        //  brandId 合法性校验
         boolean checked = dataPolicyService.brandIdCheck(brandId);
         if(!checked){
             throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
         }
         String cellId = studioCellService.initCell(brandId);
        StudioCellInitVO vo = new StudioCellInitVO().setCellId(cellId);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }


}
