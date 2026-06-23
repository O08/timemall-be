package com.norm.timemall.app.mall.domain.pojo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.mall.domain.ro.CellRO;
import lombok.Data;

@Data
public class BrandGuideResponseContext {
    private IPage<CellRO> cells;
    private MallHomeInfo homeInfo;
}
