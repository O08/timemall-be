package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsDrawerDashboardRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioFetchMpsDrawerDashboardVO extends CodeVO {
    private StudioFetchMpsDrawerDashboardRO dashboard;
}
