package com.norm.timemall.app.ms.service;

import com.norm.timemall.app.ms.domain.dto.MsReadMpsMsgDTO;
import com.norm.timemall.app.ms.domain.pojo.MsHaveNewMpsMsg;
import org.springframework.stereotype.Service;

@Service
public interface MsMpsMsgHelperService {
    void readMpsMsg(MsReadMpsMsgDTO dto);

    MsHaveNewMpsMsg haveNewMpsMsg(String rooms);
}
