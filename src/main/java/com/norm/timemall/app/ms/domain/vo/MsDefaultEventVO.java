package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import lombok.Data;

@Data
public class MsDefaultEventVO extends CodeVO {
    private MsDefaultEvent event;
}
