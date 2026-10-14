package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioEditSupplierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchCandidateSupplierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchSupplierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioReplaceSupplierNdaDTO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchCandidateSupplierVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchSupplierPageVO;
import com.norm.timemall.app.studio.service.StudioSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioSupplierController {
    @Autowired
    private StudioSupplierService studioSupplierService;
    
    @PostMapping("/api/v1/web_estudio/brand/{id}/add_as_supplier")
    public SuccessVO addAsSupplier(@PathVariable("id") String supplierBrandId) {
        studioSupplierService.addAsSupplier(supplierBrandId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    /**
     * 删除供应商，只有采购商可以删除，如果上传了了NDA文件，需要删除NDA文件
     * @param id，supplier table id
     * @return
     */
    @DeleteMapping("/api/v1/web_estudio/supplier/{id}/remove")
    public SuccessVO removeSupplier(@PathVariable("id") String id) {
        studioSupplierService.removeSupplier(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/web_estudio/supplier/edit")
    public SuccessVO editSupplier(@Validated @RequestBody StudioEditSupplierDTO dto) {
        studioSupplierService.editSupplier(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web_estudio/brand/supplier/query")
    public StudioFetchSupplierPageVO querySupplier(@Validated StudioFetchSupplierPageDTO dto) {
        StudioFetchSupplierPageVO vo = new StudioFetchSupplierPageVO();
        vo.setSupplier(studioSupplierService.querySupplierPage(dto));
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/web_estudio/supplier/nda/replace")
    public SuccessVO replaceNDA(@Validated StudioReplaceSupplierNdaDTO dto) {
        studioSupplierService.replaceNda(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/web_estudio/candidate_supplier/query")
    public StudioFetchCandidateSupplierVO queryCandidateSupplier(@Validated StudioFetchCandidateSupplierDTO dto) {
        StudioFetchCandidateSupplierVO vo = new StudioFetchCandidateSupplierVO();
        vo.setSuppliers(studioSupplierService.queryCandidateSuppliers(dto));
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
