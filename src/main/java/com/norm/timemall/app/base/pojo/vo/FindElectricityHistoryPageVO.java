package com.norm.timemall.app.base.pojo.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.ro.FindElectricityHistoryPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FindElectricityHistoryPageVO extends CodeVO {
    private IPage<FindElectricityHistoryPageRO> trans;
}
