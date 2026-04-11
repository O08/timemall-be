package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.DelPpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.NewPpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.dto.PpcLinkPageDTO;
import com.norm.timemall.app.affiliate.domain.dto.RenamePpcLinkDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcLinkPageRO;
import org.springframework.stereotype.Service;

@Service
public interface AffiliatePpcLinkService {
    IPage<PpcLinkPageRO> findPpcLinkPage(PpcLinkPageDTO dto);

    void addPpcLink(NewPpcLinkDTO dto);

    void modifyLinkName(RenamePpcLinkDTO dto);

    void removeLink(DelPpcLinkDTO dto);

}
