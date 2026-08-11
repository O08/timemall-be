package com.norm.timemall.app.affiliate.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.ro.PpcBillPageRO;
import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PpcBillPageVO extends CodeVO {
    private IPage<PpcBillPageRO> bill;
}
