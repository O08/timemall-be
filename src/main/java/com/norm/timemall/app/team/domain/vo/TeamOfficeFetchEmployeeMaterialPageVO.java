package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeFetchEmployeeMaterialPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamOfficeFetchEmployeeMaterialPageVO extends CodeVO {
    private IPage<TeamOfficeFetchEmployeeMaterialPageRO> material;
}
