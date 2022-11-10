package com.norm.timemall.app.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.CellPageDTO;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.mall.domain.vo.CellIntroVO;
import com.norm.timemall.app.mall.domain.vo.CellListVO;
import com.norm.timemall.app.mall.domain.vo.CellPageVO;
import com.norm.timemall.app.mall.service.CellListService;
import com.norm.timemall.app.mall.service.CellServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CellController {
    @Autowired
    private CellServic cellServic;
    @Autowired
    private CellListService cellListService;
    /*
     * 分页查询多个服务
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/cells")
    public CellPageVO retrieveCells(@Validated  CellPageDTO cellPageDTO)
    {
        IPage<CellRO> cells = cellServic.findCells(cellPageDTO);
        CellPageVO cellPageVO = new CellPageVO()
                .setResponseCode(CodeEnum.SUCCESS)
                .setCells(cells);
        return cellPageVO;
    }

    /*
     * 服务清单查询
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/brand/{brand_id}/celllist")
    public CellListVO retrieveCellList(@PathVariable("brand_id") String brandId)
    {
        CellListVO result = cellListService.findCellList(brandId);
        return result;
    }

    /*
     * 服务详情
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/services/{cell_id}/intro")
    public CellIntroVO retrieveCellIntro(@PathVariable("cell_id") String cellId)
    {
        CellIntroVO result = cellServic.fidCellIntro(cellId);
        return result;
    }
}
