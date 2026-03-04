package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchSellerDashBoardRO;
import lombok.Data;

@Data
public class StudioFetchSellerDashBoardVO extends CodeVO {
    private StudioFetchSellerDashBoardRO dashboard;
}
