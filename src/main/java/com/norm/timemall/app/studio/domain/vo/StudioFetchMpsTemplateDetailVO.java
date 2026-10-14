package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsTemplateDetailRO;
import lombok.Data;

@Data
public class StudioFetchMpsTemplateDetailVO extends CodeVO {
    private StudioFetchMpsTemplateDetailRO detail;
}
