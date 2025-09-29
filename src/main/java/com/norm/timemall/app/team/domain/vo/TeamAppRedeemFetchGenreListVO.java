package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemFetchGenreListRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppRedeemFetchGenreListVO extends CodeVO {
    private ArrayList<TeamAppRedeemFetchGenreListRO> genre;
}
