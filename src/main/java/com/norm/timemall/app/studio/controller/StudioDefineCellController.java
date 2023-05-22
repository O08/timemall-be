package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.DataPolicyService;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.studio.domain.dto.StudioCellIntroContentDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCellOverViewDTO;
import com.norm.timemall.app.studio.domain.dto.StudioPricingDTO;
import com.norm.timemall.app.studio.service.StudioCellService;
import com.norm.timemall.app.studio.service.StudioPricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class StudioDefineCellController {
    @Autowired
    private StudioCellService studioCellService;

    @Autowired
    private StudioPricingService studioPricingService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private DataPolicyService dataPolicyService;

    /**
     *
     *overview设置
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/service/{cell_id}/define/overview")
    public SuccessVO defineOverView(@PathVariable("cell_id") String cellId,
                                    @AuthenticationPrincipal CustomizeUser user,
                                    @RequestBody StudioCellOverViewDTO dto)
    {
        studioCellService.modifyTitleAndCanProvideInvoice(cellId,user.getUserId(),dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     *
     * 定价
     * @return
     */
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/service/{cell_id}/define/pricing")
    public SuccessVO cellPricing(@PathVariable("cell_id") String cellId, @Validated @RequestBody StudioPricingDTO dto)
    {
        // 行级权限检查， 只有服务归属方有权限定义数据
        if(!dataPolicyService.cellOwnerCheck(cellId)){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // remove old pricing
        studioPricingService.removePricing(cellId);
        // insert new pricing
        studioPricingService.newPricing(cellId,dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/services/{cell_id}/cover")
    public SuccessVO uploadCellCover(@PathVariable(value = "cell_id") String cellId,@RequestParam("file") MultipartFile file)
    {
        // 行级权限检查， 只有服务归属方有权限定义数据
        if(!dataPolicyService.cellOwnerCheck(cellId)){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // find cell
        Cell cell = studioCellService.findSingleCell(cellId);
        // store cell cover file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.CELL_COVER);
        // update cell cover uri
        studioCellService.modifyCellCover(cellId,uri);
        // delete unused file
        fileStoreService.deleteFile(cell.getCover());
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/services/{cell_id}/intro/cover")
    public SuccessVO uploadIntroCover(@PathVariable(value = "cell_id") String cellId,@RequestParam("file") MultipartFile file)
    {
        // 行级权限检查， 只有服务归属方有权限定义数据
        if(!dataPolicyService.cellOwnerCheck(cellId)){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        // find cell
        Cell cell = studioCellService.findSingleCell(cellId);
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file,FileStoreDir.CELL_INTRO_COVER);
        // update cell cover uri
        studioCellService.modifyIntroCover(cellId,uri);
        // delete unused file
        fileStoreService.deleteFile(cell.getIntroCover());
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @ResponseBody
    @PutMapping(value = "/api/v1/web_estudio/services/{cell_id}/intro/content")
    public SuccessVO putCellIntroContent(@PathVariable("cell_id") String cellId,
                                         @Validated @RequestBody StudioCellIntroContentDTO dto)
    {
        // 行级权限检查， 只有服务归属方有权限定义数据
        if(!dataPolicyService.cellOwnerCheck(cellId)){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        studioCellService.modifyCellContent(cellId,dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
