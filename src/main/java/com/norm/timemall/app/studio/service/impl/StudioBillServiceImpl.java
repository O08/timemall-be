package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBillPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioBillRO;
import com.norm.timemall.app.studio.mapper.StudioBillMapper;
import com.norm.timemall.app.studio.service.StudioBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioBillServiceImpl implements StudioBillService {

    @Autowired
    private StudioBillMapper studioBillMapper;
    @Override
    public IPage<StudioBillRO> findBills( String brandId ,StudioBrandBillPageDTO dto) {
        IPage<StudioBillRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return studioBillMapper.selectBillPageByBrandId(page, brandId,dto);
    }

    @Override
    public void markBillForBrandByIdAndCode(String billId, String code) {
        studioBillMapper.updateBillForBrandMarkById(billId,code);
    }
}
