package com.norm.timemall.app.base.pojo.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseConsumeMcpSessionVO extends CodeVO {
    private String payload;
}
