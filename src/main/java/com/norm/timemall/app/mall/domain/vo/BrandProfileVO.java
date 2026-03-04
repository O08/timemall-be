package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.BrandProfileRO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * brand profile
 */
@Data
@Accessors(chain = true)
public class BrandProfileVO extends CodeVO {
    private BrandProfileRO profile;
}
