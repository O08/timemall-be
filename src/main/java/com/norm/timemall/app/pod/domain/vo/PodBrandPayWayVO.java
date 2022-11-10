package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.pojo.PodPayway;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodBrandPayWayVO extends CodeVO {
    private PodPayway payway;
}
