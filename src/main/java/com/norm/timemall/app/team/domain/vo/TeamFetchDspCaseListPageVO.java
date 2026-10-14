package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseListPageRO;
import lombok.Data;

@Data
public class TeamFetchDspCaseListPageVO extends CodeVO {

    private IPage<TeamFetchDspCaseListPageRO> reportCase;


}
