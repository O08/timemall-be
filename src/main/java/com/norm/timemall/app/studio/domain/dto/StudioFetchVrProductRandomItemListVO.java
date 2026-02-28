package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchVrProductRandomItemListRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StudioFetchVrProductRandomItemListVO extends CodeVO {
    private ArrayList<StudioFetchVrProductRandomItemListRO> merchandise;
}
