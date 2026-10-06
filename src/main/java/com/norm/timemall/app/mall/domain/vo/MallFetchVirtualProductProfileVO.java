package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.MallFetchVirtualProductProfileRO;
import lombok.Data;

@Data
public class MallFetchVirtualProductProfileVO extends CodeVO {

    private MallFetchVirtualProductProfileRO profile;

}
