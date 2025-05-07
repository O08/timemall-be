package com.norm.timemall.app.studio.service.impl;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchFirstSupplier;
import com.norm.timemall.app.studio.domain.ro.StudioFetchFirstSupplierRO;
import com.norm.timemall.app.studio.mapper.StudioCommercialPaperMapper;
import com.norm.timemall.app.studio.service.StudioFirstSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StudioFirstSupplierServiceImpl implements StudioFirstSupplierService {
    @Autowired
    private StudioCommercialPaperMapper studioCommercialPaperMapper;
    @Override
    public StudioFetchFirstSupplier findFirstSupplier(String q) {

        String brandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<StudioFetchFirstSupplierRO> ros = studioCommercialPaperMapper.selectFirstSupplierByBrandId(brandId,q);
        StudioFetchFirstSupplier supplier=new StudioFetchFirstSupplier();
        supplier.setRecords(ros);
        return supplier;

    }
}
