package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.pojo.CellListInfo;
import com.norm.timemall.app.mall.domain.ro.CellListRO;
import com.norm.timemall.app.mall.domain.vo.CellIntroVO;
import com.norm.timemall.app.mall.domain.vo.CellListVO;
import com.norm.timemall.app.mall.mapper.CellListMapper;
import com.norm.timemall.app.mall.service.CellListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CellListServiceImpl implements CellListService {

    @Autowired
    private CellListMapper cellListMapper;
    @Override
    public CellListVO findCellList(String brandId) {
        CellListInfo cellListInfo = cellListMapper.selectCellListByBrandId(brandId);
        CellListVO result = new CellListVO().setResponseCode(CodeEnum.SUCCESS)
                .setLists(cellListInfo);
        return result;
    }

}
