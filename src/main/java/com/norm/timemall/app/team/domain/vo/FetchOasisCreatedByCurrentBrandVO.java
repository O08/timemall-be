package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.OasisCreatedByCurrentBrand;
import lombok.Data;

@Data
public class FetchOasisCreatedByCurrentBrandVO extends CodeVO {
    private OasisCreatedByCurrentBrand oasis;
}
