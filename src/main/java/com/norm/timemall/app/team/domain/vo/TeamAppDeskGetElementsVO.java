package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppDeskGetElementsRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppDeskGetElementsVO  extends CodeVO {

    private ArrayList<TeamAppDeskGetElementsRO> apps;

}
