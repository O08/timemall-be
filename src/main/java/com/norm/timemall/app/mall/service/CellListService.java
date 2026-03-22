package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.vo.CellListVO;
import org.springframework.stereotype.Service;

@Service
public interface CellListService {
    CellListVO findCellList(String brandId);

}
