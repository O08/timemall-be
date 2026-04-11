package com.norm.timemall.app.mall.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveProductListPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallRetrieveProductListPageVO extends CodeVO {
    private IPage<MallRetrieveProductListPageRO> product;
}
