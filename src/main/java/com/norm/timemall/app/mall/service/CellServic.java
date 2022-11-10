package com.norm.timemall.app.mall.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.dto.CellPageDTO;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import com.norm.timemall.app.mall.domain.vo.CellIntroVO;
import org.springframework.stereotype.Service;

@Service
public interface CellServic {
    IPage<CellRO> findCells(CellPageDTO servicePageDTO);

    CellIntroVO fidCellIntro(String cellId);
}
