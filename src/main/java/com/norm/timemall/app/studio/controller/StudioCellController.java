package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CellMarkEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.pojo.vo.CellIntroVO;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import com.norm.timemall.app.studio.domain.vo.StudioCellInitVO;
import com.norm.timemall.app.studio.domain.vo.StudioCellPageVO;
import com.norm.timemall.app.studio.service.StudioCellService;
import com.norm.timemall.app.studio.service.StudioPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioCellController {
    @Autowired
    private StudioCellService studioCellService;

    @Autowired
    private DataPolicyService dataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private StudioPricingService studioPricingService;
    /*
     * 分页查询归属当前商家多个服务
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/{brand_id}/cells")
    public StudioCellPageVO retrieveCells(@PathVariable("brand_id") String brandId, @Validated PageDTO cellPageDTO)
    {
        IPage<CellInfoRO> cells = studioCellService.findCells(brandId,cellPageDTO);
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

        boolean checked = dataPolicyService.cellOwnerCheck(cellId);
        if(!checked){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
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
    /**
     * 移出一条服务，服务必须为草稿状态
     * @param cellId
     * @return
     */
    @ResponseBody
    @DeleteMapping(value = "/api/v1/web_estudio/services/{cell_id}/trash")
    public SuccessVO trashCell(@PathVariable("cell_id") String cellId)
    {
        Cell cell = studioCellService.findSingleCell(cellId);
        if(!CellMarkEnum.DRAFT.getMark().equals(cell.getMark())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // remove cover img
        fileStoreService.deleteFile(cell.getCover());
        // remove intro banner
        fileStoreService.deleteFile(cell.getIntroCover());
        // remove pricing
        studioPricingService.removePricing(cellId);
        // remove cell
        studioCellService.trashCell(cellId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /*
     * 服务详情
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/cell/{cell_id}/profile")
    public CellIntroVO retrieveCellIntroForStudio(@PathVariable("cell_id") String cellId)
    {
        CellIntroVO result = studioCellService.findCellProfileInfo(cellId);
        return result;
    }


}
