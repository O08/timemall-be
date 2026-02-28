package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.FetchVirtualProductMetaInfoRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchVirtualProductMetaInfoVO extends CodeVO {

    private FetchVirtualProductMetaInfoRO product;

}
