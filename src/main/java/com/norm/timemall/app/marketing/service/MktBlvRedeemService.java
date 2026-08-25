package com.norm.timemall.app.marketing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.marketing.domain.dto.MktClaimRedeemDTO;
import com.norm.timemall.app.marketing.domain.ro.MktFetchRedeemHistoryPageRO;
import org.springframework.stereotype.Service;

@Service
public interface MktBlvRedeemService {
    IPage<MktFetchRedeemHistoryPageRO> findRedeemHistory(PageDTO dto);

    void claim(MktClaimRedeemDTO dto);
}
