package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioGetShoppingSubscriptionMetaInfoRO;
import lombok.Data;

@Data
public class StudioGetShoppingSubscriptionMetaInfoVO extends CodeVO {
    private StudioGetShoppingSubscriptionMetaInfoRO meta;
}
