package com.norm.timemall.app.base.pojo.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class NewOrderVO extends CodeVO {
    private NewOrderRO order;
}
