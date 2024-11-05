package com.norm.timemall.app.mall.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.BrandCellsPageDTO;
import com.norm.timemall.app.mall.domain.dto.BrandGuideDTO;
import com.norm.timemall.app.mall.domain.dto.CellPageDTO;
import com.norm.timemall.app.base.pojo.ro.CellIntroRO;
import com.norm.timemall.app.base.pojo.vo.CellIntroVO;
import com.norm.timemall.app.mall.domain.pojo.Fee;
import com.norm.timemall.app.mall.domain.pojo.MallCellPricing;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeCell;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.domain.ro.MallFetchMarqueeCellRO;
import com.norm.timemall.app.mall.domain.vo.CellPricingVO;
import com.norm.timemall.app.mall.mapper.CellMapper;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.mall.mapper.PricingMapper;
import com.norm.timemall.app.mall.service.CellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CellServiceImpl implements CellService {
    @Autowired
    private CellMapper cellMapper;
    @Autowired
    private PricingMapper pricingMapper;

    @Override
    public IPage<CellRO> findCells(CellPageDTO cellPageDTO) {
        Page<CellRO> page = new Page<>();
        page.setSize(cellPageDTO.getSize());
        page.setCurrent(cellPageDTO.getCurrent());
        return cellMapper.selectCellPage(page, cellPageDTO);
    }

    @Override
    public CellIntroVO findCellIntro(String cellId) {
        CellIntroRO intro = cellMapper.selectCellIntro(cellId);
        CellIntroVO result = new CellIntroVO().setResponseCode(CodeEnum.SUCCESS)
                .setProfile(intro);
        return result;
    }

    @Override
    public IPage<CellRO> findBrandCells(BrandCellsPageDTO dto) {
        Page<CellRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return cellMapper.selectBrandCellPage(page, dto);
    }


    @Override
    public MallHomeInfo findHomeInfo(BrandGuideDTO dto) {
        return cellMapper.selectHomeInfoByBrandIdOrHandle(dto);
    }

    @Override
    public CellPricingVO findCellPricing(String cellId) {
        ArrayList<Fee> fees =pricingMapper.selectFeeList(cellId);

        MallCellPricing pricing = new MallCellPricing();
        pricing.setFee(fees);

        CellPricingVO vo = new CellPricingVO();
        vo.setPricing(pricing);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public MallFetchMarqueeCell findMarqueeCell() {

        ArrayList<MallFetchMarqueeCellRO> ros = cellMapper.selectMarqueeCell();
        MallFetchMarqueeCell cell = new MallFetchMarqueeCell();
        cell.setRecords(ros);
        return cell;

    }
}
