package com.norm.timemall.app.pod.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodFetchVirtualOrderPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PodFetchVirtualOrderPageVO extends CodeVO {

    private IPage<PodFetchVirtualOrderPageRO> order;

}
