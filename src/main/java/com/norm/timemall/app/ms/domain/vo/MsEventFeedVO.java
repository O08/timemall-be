package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsEventFeed;
import lombok.Data;

@Data
public class MsEventFeedVO extends CodeVO {
    private MsEventFeed eventFeed;
}
