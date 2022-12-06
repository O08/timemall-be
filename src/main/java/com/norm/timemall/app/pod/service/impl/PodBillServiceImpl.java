package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.mo.Bill;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.pod.domain.dto.PodBillPageDTO;
import com.norm.timemall.app.pod.domain.ro.PodBillsRO;
import com.norm.timemall.app.pod.mapper.PodBillMapper;
import com.norm.timemall.app.pod.service.PodBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodBillServiceImpl implements PodBillService {
    @Autowired
    private PodBillMapper podBillMapper;
    @Override
    public Bill findBillByIdAndCustomer(String billId, String customerId) {
        Bill bill =  podBillMapper.selectByIdAndCustomer( billId,  customerId);
        return bill;
    }

    @Override
    public void modifyBillVoucherUri(String billId, String uri) {
        podBillMapper.updateBillVoucherById(billId,uri);

    }

    @Override
    public void markBillByIdAndCode(String billId, String code) {
        podBillMapper.updateBillMarkById(billId,code);
    }

    @Override
    public IPage<PodBillsRO> findBills(PodBillPageDTO pageDTO, CustomizeUser user) {
        IPage<PodBillsRO> page = new Page<>();
        page.setCurrent(pageDTO.getCurrent());
        page.setSize(pageDTO.getSize());
        IPage<PodBillsRO>  bills = podBillMapper.selectBillPageByUserId(page, pageDTO.getCode(),user.getUserId());
        return bills;
    }
}
