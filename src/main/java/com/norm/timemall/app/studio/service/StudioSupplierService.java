package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.studio.domain.dto.StudioEditSupplierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchCandidateSupplierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchSupplierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioReplaceSupplierNdaDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCandidateSupplierRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSupplierPageRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface StudioSupplierService {
    void addAsSupplier(String supplierBrandId);
    
    void removeSupplier(String id);
    
    void editSupplier(StudioEditSupplierDTO dto);
    
    IPage<StudioFetchSupplierPageRO> querySupplierPage(StudioFetchSupplierPageDTO dto);
    
    void replaceNda(StudioReplaceSupplierNdaDTO dto);
    
    ArrayList<StudioFetchCandidateSupplierRO> queryCandidateSuppliers(StudioFetchCandidateSupplierDTO dto);
}
