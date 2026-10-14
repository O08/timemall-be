package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedsPageRO;
import lombok.Data;

@Data
public class TeamAppFbFetchFeedsPageVO extends CodeVO {

   private IPage<TeamAppFbFetchFeedsPageRO> feed;

}
