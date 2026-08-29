package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisRolesRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamFetchOasisRolesVO extends CodeVO {
  private ArrayList<TeamFetchOasisRolesRO> role;
}
