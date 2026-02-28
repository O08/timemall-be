package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseLibraryPageRO;
import lombok.Data;

@Data
public class TeamFetchDspCaseLibraryPageVO  extends CodeVO {

    private IPage<TeamFetchDspCaseLibraryPageRO> reportCase;

}
