package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppLinkShoppingFetchFeedPageRO;
import lombok.Data;

@Data
public class TeamAppLinkShoppingFetchFeedPageVO extends CodeVO {
    private IPage<TeamAppLinkShoppingFetchFeedPageRO> feed;
}
