package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchCommentPageRO;
import lombok.Data;

@Data
public class TeamAppViberFetchCommentPageVO  extends CodeVO {
    private IPage<TeamAppViberFetchCommentPageRO> comment;
}
