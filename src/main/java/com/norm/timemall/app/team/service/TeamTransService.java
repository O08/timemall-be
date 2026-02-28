package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import org.springframework.stereotype.Service;

@Service
public interface TeamTransService {
    IPage<TeamTrans>  findTrans(PageDTO dto);

}
