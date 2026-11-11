package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import lombok.Data;

@Data
public class TeamTransPageVO extends CodeVO {
    private IPage<TeamTrans> trans;
}
