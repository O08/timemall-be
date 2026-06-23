package com.norm.timemall.app.studio.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsFund;
import lombok.Data;

@Data
public class StudioFetchMpsFundVO  extends CodeVO {
    private StudioFetchMpsFund fund;
}
