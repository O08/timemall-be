package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchOutreachChannelPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenameChannelNameDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchOutreachChannelPageRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliateOutreachChannelService {
    void modifyChannelName(RenameChannelNameDTO dto);

    void removeChannel(String outreachChannelId);

    void addOneChannel(String outreachName);

    IPage<FetchOutreachChannelPageRO> findOutreachChannelRecord(FetchOutreachChannelPageDTO dto);
}
