package com.norm.timemall.app.mall.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.BrandCellsPageDTO;
import com.norm.timemall.app.mall.domain.dto.CellPageDTO;
import com.norm.timemall.app.base.pojo.ro.CellIntroRO;
import com.norm.timemall.app.base.pojo.vo.CellIntroVO;
import com.norm.timemall.app.mall.domain.pojo.MallHomeInfo;
import com.norm.timemall.app.mall.mapper.CellMapper;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.mall.service.CellServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellServicImpl implements CellServic {
    @Autowired
    private CellMapper cellMapper;
    @Override
    public IPage<CellRO> findCells(CellPageDTO cellPageDTO) {
        Page<CellRO> page = new Page<>();
        page.setSize(cellPageDTO.getSize());
        page.setCurrent(cellPageDTO.getCurrent());
        return cellMapper.selectCellPage(page, cellPageDTO);
    }

    @Override
    public CellIntroVO fidCellIntro(String cellId) {
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
    public MallHomeInfo findHomeInfo(String brandId) {
        MallHomeInfo data = cellMapper.selectHomeInfoByBrandId(brandId);
        return data;
    }
}
