package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.pojo.vo.CellIntroVO;
import com.norm.timemall.app.studio.domain.dto.StudioCellIntroContentDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCellOverViewDTO;
import com.norm.timemall.app.studio.domain.ro.StudioCellRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioCellService {
    IPage<StudioCellRO> findCells(String brandId,PageDTO cellPageDTO);

    void modifyTitle(String cellId, String userId,StudioCellOverViewDTO dto);

    Cell findSingleCell(String cellId);

    void modifyCellCover(String cellId, String uri);

    void modifyIntroCover(String cellId, String uri);

    void modifyCellContent(String cellId, StudioCellIntroContentDTO dto);

    void markCell(String cellId, String code);

    String initCell(String brandId);

    void trashCell(String cellId);

    CellIntroVO findCellProfileInfo(String cellId);

}
