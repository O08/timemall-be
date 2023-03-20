package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import org.springframework.stereotype.Service;

@Service
public interface TeamTransService {
    TeamTrans findTrans(String fid,String fidType,String year,String month);

}
