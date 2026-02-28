package com.norm.timemall.app.studio.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.FetchSemiDataRO;
import lombok.Data;

@Data
public class FetchSemiDataPageVO extends CodeVO {
    private IPage<FetchSemiDataRO> semi;
}
