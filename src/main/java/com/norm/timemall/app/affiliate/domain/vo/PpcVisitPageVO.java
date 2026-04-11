package com.norm.timemall.app.affiliate.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.ro.PpcVisitPageRO;
import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PpcVisitPageVO  extends CodeVO {
    private IPage<PpcVisitPageRO> visit;
}
