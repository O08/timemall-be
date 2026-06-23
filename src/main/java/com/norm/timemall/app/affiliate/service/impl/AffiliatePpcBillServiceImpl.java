package com.norm.timemall.app.affiliate.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.affiliate.domain.dto.PpcBillPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcBillPageRO;
import com.norm.timemall.app.affiliate.mapper.AffiliatePpcBillMapper;
import com.norm.timemall.app.affiliate.service.AffiliatePpcBillService;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AffiliatePpcBillServiceImpl implements AffiliatePpcBillService {
    @Autowired
    private AffiliatePpcBillMapper affiliatePpcBillMapper;
    @Override
    public IPage<PpcBillPageRO> findPpcBillPage(PpcBillPageDTO dto) {

        IPage<PpcBillPageRO> page= new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return affiliatePpcBillMapper.selectPageByDTO(page, SecurityUserHelper.getCurrentPrincipal().getBrandId(), dto);

    }
}
