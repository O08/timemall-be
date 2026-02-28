package com.norm.timemall.app.pod.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.PodTransRO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PodTransPageVO extends CodeVO {
    /**
     * 响应数据
     */
    private IPage<PodTransRO> transactions;
}
