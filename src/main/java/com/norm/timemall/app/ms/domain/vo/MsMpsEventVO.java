package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsMpsEvent;
import lombok.Data;

@Data
public class MsMpsEventVO extends CodeVO {
    private MsMpsEvent event;
}
