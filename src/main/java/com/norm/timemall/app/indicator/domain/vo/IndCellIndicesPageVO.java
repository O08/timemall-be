package com.norm.timemall.app.indicator.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.indicator.domain.ro.IndCellIndicesRO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IndCellIndicesPageVO extends CodeVO {
    /**
     * 响应数据
     */
    private IPage<IndCellIndicesRO> cells;
}
