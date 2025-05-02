package com.norm.timemall.app.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SbuEnum;
import com.norm.timemall.app.mall.domain.dto.BrandCellsPageDTO;
import com.norm.timemall.app.mall.domain.dto.BrandGuideDTO;
import com.norm.timemall.app.mall.domain.pojo.BrandGuideResponseContext;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.mall.domain.vo.BrandGuideVO;
import com.norm.timemall.app.mall.domain.vo.BrandProfileVO;
import com.norm.timemall.app.mall.service.BrandService;
import com.norm.timemall.app.mall.service.CellService;
import com.norm.timemall.app.mall.service.MallVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;
    @Autowired
    private CellService cellService;

    @Autowired
    private MallVirtualProductService mallVirtualProductService;
    /*
     * 供应商资料
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/brand/{brand_id}/profile")
    public BrandProfileVO retrieveCellIntro(@PathVariable("brand_id") String brandId)
    {
        BrandProfileVO result = brandService.findBrandProfile(brandId);
        return result;
    }

    @PostMapping(value = "/api/v1/web_mall/brand/guide")
    public BrandGuideVO fetchBrandGuide(@Validated @RequestBody BrandGuideDTO dto){
        BrandGuideResponseContext responseContext = new BrandGuideResponseContext();
         //fetch home info
        MallHomeInfo homeInfo = mallVirtualProductService.findHomeInfo(dto);
        responseContext.setHomeInfo(homeInfo);

        // fetch  cells
        if (homeInfo!=null){
            String brandId=homeInfo.getBrowseBrandId();
            BrandCellsPageDTO brandCellsPageDTO=new BrandCellsPageDTO();
            brandCellsPageDTO.setBrandId(brandId)
                            .setSbu(SbuEnum.HOUR.getValue())
                                    .setCurrent(1L)
                                            .setSize(12L);

            IPage<CellRO> brandCells = cellService.findBrandCells(brandCellsPageDTO);
            responseContext.setCells(brandCells);
        }
        if(homeInfo==null){
            Page<CellRO> emptyCellsObj = new Page<>();
            emptyCellsObj.setSize(12L);
            emptyCellsObj.setCurrent(1L);
            responseContext.setCells(emptyCellsObj);
            MallHomeInfo emptyHomeInfo = new MallHomeInfo();
            emptyHomeInfo.setVr(new ArrayList<>());
            responseContext.setHomeInfo(emptyHomeInfo);
        }

        BrandGuideVO vo =new BrandGuideVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setResponseContext(responseContext);
        return  vo;

    }
}
