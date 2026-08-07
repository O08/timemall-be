package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchOnePostRO;
import lombok.Data;

@Data
public class TeamAppViberFetchPostPageVO extends CodeVO {
    private IPage<TeamAppViberFetchOnePostRO> feed;
}
