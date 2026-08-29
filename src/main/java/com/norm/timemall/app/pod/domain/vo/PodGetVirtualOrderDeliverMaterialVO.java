package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodGetVirtualOrderDeliverMaterialRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PodGetVirtualOrderDeliverMaterialVO extends CodeVO {

    private PodGetVirtualOrderDeliverMaterialRO deliver;

}
