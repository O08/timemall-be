package com.norm.timemall.app.mall.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.FetchBluvarrierRO;
import lombok.Data;

@Data
public class FetchBluvarrierVO extends CodeVO {
    private FetchBluvarrierRO bluvarrier;
}
