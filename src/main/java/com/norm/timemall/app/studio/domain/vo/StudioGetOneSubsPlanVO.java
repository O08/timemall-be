package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioGetOneSubsPlanRO;
import lombok.Data;

@Data
public class StudioGetOneSubsPlanVO extends CodeVO {
    private StudioGetOneSubsPlanRO plan;
}
