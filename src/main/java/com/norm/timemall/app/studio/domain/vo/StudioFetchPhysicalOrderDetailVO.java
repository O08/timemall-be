package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchPhysicalOrderDetailRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioFetchPhysicalOrderDetailVO extends CodeVO {
    private StudioFetchPhysicalOrderDetailRO detail;
}
