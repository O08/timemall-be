package com.norm.timemall.app.ms.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import lombok.Data;

@Data
public class MsDefaultEventPageVO  extends CodeVO {
    private IPage<MsDefaultEventCard> event;
}
