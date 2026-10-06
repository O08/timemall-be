package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioGetElectricityProductInfoRO;
import lombok.Data;

@Data
public class StudioGetElectricityProductInfoVO extends CodeVO {
    private StudioGetElectricityProductInfoRO product;
}
