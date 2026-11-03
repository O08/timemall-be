package com.norm.timemall.app.pod.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodFetchCoopBrandApplicationsPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PodFetchCoopBrandApplicationsPageVO extends CodeVO {
    private IPage<PodFetchCoopBrandApplicationsPageRO> application;
}
