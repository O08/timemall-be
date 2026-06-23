package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchCandidateSupplierRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioFetchCandidateSupplierVO extends CodeVO {
    private ArrayList<StudioFetchCandidateSupplierRO> suppliers;
}
