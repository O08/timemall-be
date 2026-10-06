package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsMpsRoom;
import lombok.Data;

@Data
public class MsGetMpsRoomVO  extends CodeVO {
    private MsMpsRoom room;
}
