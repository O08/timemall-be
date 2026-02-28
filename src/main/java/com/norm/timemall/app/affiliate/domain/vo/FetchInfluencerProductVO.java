package com.norm.timemall.app.affiliate.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.norm.timemall.app.affiliate.domain.ro.FetchInfluencerProductRO;
import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchInfluencerProductVO extends CodeVO {
    private IPage<FetchInfluencerProductRO> product;
}
