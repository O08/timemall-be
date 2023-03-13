package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import lombok.Data;

@Data
public class TeamObjPageVO extends CodeVO {
    private IPage<TeamObjRO> obj;
}
