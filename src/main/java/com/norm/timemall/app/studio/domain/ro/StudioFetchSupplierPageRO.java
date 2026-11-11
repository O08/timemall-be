package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchSupplierPageRO {
    /**
     * supplier table id
     */
    private String id;
    private String biz;
    private String canProvideInvoice;
    private String compliance;
    // supplier table create_at
    private String createAt;
    private String ndaFileUri;
    private String supplierAvatar;
    private String supplierBrandId;
    private String supplierBrandMark;
    private String supplierLevel;
    // brand table modified_at
    private String supplierLoginAt;
    private String supplierName;
    private String supplierStatus;
    private String supplierUserId;
    private String ux;
}
