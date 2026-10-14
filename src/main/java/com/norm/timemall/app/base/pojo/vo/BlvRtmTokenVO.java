package com.norm.timemall.app.base.pojo.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BlvRtmTokenVO extends CodeVO {
    private String token;
    private String userId;
    private String userName;
}
