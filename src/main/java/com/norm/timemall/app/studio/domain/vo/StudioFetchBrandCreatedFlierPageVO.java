package com.norm.timemall.app.studio.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchBrandCreatedFlierPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudioFetchBrandCreatedFlierPageVO extends CodeVO {
    private IPage<StudioFetchBrandCreatedFlierPageRO> flier;
}
