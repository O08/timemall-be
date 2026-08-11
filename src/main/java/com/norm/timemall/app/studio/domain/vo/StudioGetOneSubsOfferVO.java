package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioGetOneSubsOfferRO;
import lombok.Data;

@Data
public class StudioGetOneSubsOfferVO extends CodeVO {
    private StudioGetOneSubsOfferRO offer;
}
