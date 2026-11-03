package com.norm.timemall.app.marketing.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.marketing.domain.ro.MktFetchRedeemHistoryPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MktFetchRedeemHistoryPageVO extends CodeVO {
    private IPage<MktFetchRedeemHistoryPageRO> redeemHistory;
}
