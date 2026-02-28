package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;

@Data
public class RtmTokenVO extends CodeVO {
    private String token;
    private String appId;
}
