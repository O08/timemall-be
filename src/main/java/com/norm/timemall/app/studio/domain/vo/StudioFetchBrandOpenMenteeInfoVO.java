package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandOpenMenteeInfoRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioFetchBrandOpenMenteeInfoVO extends CodeVO {
    private ArrayList<StudioFetchBrandOpenMenteeInfoRO> mentee;
}
