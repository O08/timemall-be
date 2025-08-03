package com.norm.timemall.app.studio.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsOfferPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioGetSubsOfferPageVO extends CodeVO {
    private IPage<StudioGetSubsOfferPageRO> offer;
}
