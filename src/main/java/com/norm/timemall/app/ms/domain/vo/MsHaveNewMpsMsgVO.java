package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsHaveNewMpsMsg;
import lombok.Data;

@Data
public class MsHaveNewMpsMsgVO extends CodeVO {
    private MsHaveNewMpsMsg ids;
}
