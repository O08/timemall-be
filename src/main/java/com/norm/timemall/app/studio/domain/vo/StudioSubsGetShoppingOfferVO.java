package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioSubsGetShoppingOfferRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioSubsGetShoppingOfferVO extends CodeVO {
    private StudioSubsGetShoppingOfferRO offer;
}
