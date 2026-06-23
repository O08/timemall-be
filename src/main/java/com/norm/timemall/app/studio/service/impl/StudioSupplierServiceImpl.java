package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.SupplierLevelEnum;
import com.norm.timemall.app.base.enums.SupplierStatusEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.Supplier;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.studio.domain.dto.StudioEditSupplierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchCandidateSupplierDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchSupplierPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioReplaceSupplierNdaDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCandidateSupplierRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSupplierPageRO;
import com.norm.timemall.app.studio.mapper.StudioBrandMapper;
import com.norm.timemall.app.studio.mapper.StudioSupplierMapper;
import com.norm.timemall.app.studio.service.StudioSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class StudioSupplierServiceImpl implements StudioSupplierService {

    @Autowired
    private StudioSupplierMapper studioSupplierMapper;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private StudioBrandMapper studioBrandMapper;

    @Override
    public void addAsSupplier(String supplierBrandId) {
        // Get current user's brand ID as purchaser
        String purchaserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        //  supplier and purchaser relationship
        if (purchaserBrandId.equals(supplierBrandId)) {
            throw new QuickMessageException("Purchaser and supplier cannot be the same");
        }

        Brand supplierBrand = studioBrandMapper.selectById(supplierBrandId);

        if(ObjectUtil.isNull(supplierBrand)){
            throw new QuickMessageException("Supplier not found");
        }

        // Check if supplier already exists for this purchaser
        LambdaQueryWrapper<Supplier> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Supplier::getSupplierBrandId, supplierBrandId)
                .eq(Supplier::getPurchaserBrandId, purchaserBrandId);
        Supplier existing = studioSupplierMapper.selectOne(wrapper);

        if (ObjectUtil.isNotNull(existing)) {
            throw new QuickMessageException("Supplier already exists for this purchaser");
        }

        // Create new supplier
        Supplier supplier = new Supplier();
        supplier.setId(IdUtil.simpleUUID())
                .setSupplierBrandId(supplierBrandId)
                .setPurchaserBrandId(purchaserBrandId)
                .setBiz("")
                .setNdaFileUri("")
                .setSupplierLevel(SupplierLevelEnum.ROUTINE.getMark())
                .setSupplierStatus(SupplierStatusEnum.ACTIVE.getMark())
                .setUx(60)
                .setCompliance(60)
                .setCanProvideInvoice(SwitchCheckEnum.CLOSE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioSupplierMapper.insert(supplier);
    }

    @Override
    public void removeSupplier(String id) {
        // Get current user's brand ID as purchaser
        String purchaserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // Get the supplier by ID
        Supplier supplier = studioSupplierMapper.selectById(id);

        if (ObjectUtil.isNull(supplier)) {
            throw new QuickMessageException("Supplier not found");
        }

        // Verify that the current user owns this supplier relationship
        if (!purchaserBrandId.equals(supplier.getPurchaserBrandId())) {
            throw new QuickMessageException("Only the purchaser can delete this supplier");
        }

        // Delete NDA file if it exists
        if (ObjectUtil.isNotEmpty(supplier.getNdaFileUri())) {
            fileStoreService.deleteFile(supplier.getNdaFileUri());
        }

        // Delete the supplier record
        studioSupplierMapper.deleteById(id);
    }

    @Override
    public void editSupplier(StudioEditSupplierDTO dto) {
        // Get current user's brand ID as purchaser
        String purchaserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        // Get the supplier by ID
        Supplier supplier = studioSupplierMapper.selectById(dto.getId());
        
        if (ObjectUtil.isNull(supplier)) {
            throw new QuickMessageException("Supplier not found");
        }
        
        // Verify that the current user owns this supplier relationship
        if (!purchaserBrandId.equals(supplier.getPurchaserBrandId())) {
            throw new QuickMessageException("Only the purchaser can edit this supplier");
        }
        
        // Update supplier fields
        supplier.setBiz(dto.getBiz());

        supplier.setCanProvideInvoice(dto.getCanProvideInvoice());


        supplier.setCompliance(dto.getCompliance());


        supplier.setUx(dto.getUx());


        supplier.setSupplierLevel(dto.getSupplierLevel());


        supplier.setSupplierStatus(dto.getSupplierStatus());


        // Update modified timestamp
        supplier.setModifiedAt(new Date());
        
        // Update the supplier record
        studioSupplierMapper.updateById(supplier);
    }

    @Override
    public IPage<StudioFetchSupplierPageRO> querySupplierPage(StudioFetchSupplierPageDTO dto) {
        // Get current user's brand ID as purchaser
        String purchaserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        
        // Create page object
        IPage<StudioFetchSupplierPageRO> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        
        // Query suppliers for this purchaser
        return studioSupplierMapper.selectSupplierPageForPurchaser(page, dto, purchaserBrandId);
    }

    @Override
    public void replaceNda(StudioReplaceSupplierNdaDTO dto) {
        if(dto.getMaterial() == null || dto.getMaterial().isEmpty()){
            throw new QuickMessageException("Material cannot be empty");
        }
        // Get current user's brand ID as purchaser
        String purchaserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        
        // Get the supplier by ID
        Supplier supplier = studioSupplierMapper.selectById(dto.getId());
        
        if (ObjectUtil.isNull(supplier)) {
            throw new QuickMessageException("Supplier not found");
        }
        
        // Verify that the current user owns this supplier relationship
        if (!purchaserBrandId.equals(supplier.getPurchaserBrandId())) {
            throw new QuickMessageException("Only the purchaser can replace NDA for this supplier");
        }

        String oldNdaUri = supplier.getNdaFileUri();

        // Store new NDA file
        String newNdaUri = fileStoreService.storeWithLimitedAccess(dto.getMaterial(), FileStoreDir.SUPPLIER_NDA_MATERIAL);


        // Update supplier with new NDA URI
        supplier.setNdaFileUri(newNdaUri);
        supplier.setModifiedAt(new Date());
        
        // Update the supplier record
        studioSupplierMapper.updateById(supplier);

        // Delete old NDA file if it exists
        if (ObjectUtil.isNotEmpty(supplier.getNdaFileUri())) {
            fileStoreService.deleteFile(oldNdaUri);
        }
    }

    @Override
    public ArrayList<StudioFetchCandidateSupplierRO> queryCandidateSuppliers(StudioFetchCandidateSupplierDTO dto) {
        // Get current user's brand ID as purchaser
        String purchaserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // Query candidate suppliers
        return studioSupplierMapper.selectCandidateSuppliers(purchaserBrandId, dto.getQ());
    }
}




