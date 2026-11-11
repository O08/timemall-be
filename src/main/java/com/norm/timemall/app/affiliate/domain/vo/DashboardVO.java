package com.norm.timemall.app.affiliate.domain.vo;

import com.norm.timemall.app.affiliate.domain.ro.DashboardRO;
import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class DashboardVO extends CodeVO {
    private DashboardRO dashboard;
}
