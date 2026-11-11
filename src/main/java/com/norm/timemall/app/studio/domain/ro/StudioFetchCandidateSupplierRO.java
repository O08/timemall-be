package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchCandidateSupplierRO {
    /**
     * 1-add, 0 - not add
     */
    private String added;
    private String avatar;
    private String brandId;
    private String brandName;
}
