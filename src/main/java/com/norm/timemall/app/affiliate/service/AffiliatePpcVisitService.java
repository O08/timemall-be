package com.norm.timemall.app.affiliate.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.PpcNewVisitDTO;
import com.norm.timemall.app.affiliate.domain.dto.PpcVisitPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.PpcVisitPageRO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;

@Service
public interface AffiliatePpcVisitService {
    void newVisitLog(PpcNewVisitDTO dto);

    IPage<PpcVisitPageRO> findVisitPage(PpcVisitPageDTO dto);

    ByteArrayInputStream loadPpcVisitRecord(PpcVisitPageDTO dto);
}
