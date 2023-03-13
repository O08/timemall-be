package com.norm.timemall.app.studio.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.ro.CellInfoRO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class StudioCellPageVO extends CodeVO {
    /**
     * 响应数据
     */
    private IPage<CellInfoRO> cells;
}
