package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneEvent;
import lombok.Data;

@Data
public class MsMillstoneEventVO extends CodeVO {
    private MsMillstoneEvent event;
}
