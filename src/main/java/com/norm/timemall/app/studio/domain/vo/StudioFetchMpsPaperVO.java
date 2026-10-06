package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaper;
import lombok.Data;

@Data
public class StudioFetchMpsPaperVO extends CodeVO {
    private StudioFetchMpsPaper paper;
}
