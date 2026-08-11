package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.BrandPayway;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodBrandPayWayVO extends CodeVO {
    private BrandPayway payway;
}
