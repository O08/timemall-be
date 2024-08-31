package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.FetchOasisChannelListRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchOasisChannelListVO  extends CodeVO {
    private ArrayList<FetchOasisChannelListRO> channel;
}
