package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchFirstSupplier;
import com.norm.timemall.app.studio.domain.vo.StudioFetchFirstSupplierVO;
import com.norm.timemall.app.studio.service.StudioFirstSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioFirstSupplierController {
    @Autowired
    private StudioFirstSupplierService studioFirstSupplierService;
    @GetMapping(value = "/api/v1/web_estudio/mps_chain/supplier")
    public StudioFetchFirstSupplierVO fetchFirstSupplier(String q){

        StudioFetchFirstSupplier supplier = studioFirstSupplierService.findFirstSupplier(q);
        StudioFetchFirstSupplierVO vo = new StudioFetchFirstSupplierVO();
        vo.setSupplier(supplier);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }
}
