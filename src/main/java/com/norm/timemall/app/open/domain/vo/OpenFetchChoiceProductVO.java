package com.norm.timemall.app.open.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.open.domain.ro.OpenFetchChoiceProductRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenFetchChoiceProductVO extends CodeVO {
    private IPage<OpenFetchChoiceProductRO> product;
}
