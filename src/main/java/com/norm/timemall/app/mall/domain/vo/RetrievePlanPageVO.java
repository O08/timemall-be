package com.norm.timemall.app.mall.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.RetrievePlanRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RetrievePlanPageVO extends CodeVO {
    private IPage<RetrievePlanRO> plans;
}
