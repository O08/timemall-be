package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.FetchBrandMpsMetricsRO;
import lombok.Data;

@Data
public class FetchBrandMpsMetricsVO extends CodeVO {
    private FetchBrandMpsMetricsRO metric;
}
