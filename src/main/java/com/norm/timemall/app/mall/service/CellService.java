package com.norm.timemall.app.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.dto.BrandCellsPageDTO;
import com.norm.timemall.app.mall.domain.dto.CellPageDTO;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeCell;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.base.pojo.vo.CellIntroVO;
import com.norm.timemall.app.mall.domain.vo.CellPricingVO;
import org.springframework.stereotype.Service;

@Service
public interface CellService {
    IPage<CellRO> findCells(CellPageDTO servicePageDTO);

    CellIntroVO findCellIntro(String cellId);

    IPage<CellRO> findBrandCells(BrandCellsPageDTO dto);




    CellPricingVO findCellPricing(String cellId);

    MallFetchMarqueeCell findMarqueeCell();

}
