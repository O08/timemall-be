package com.norm.timemall.app.base.pojo.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.base.pojo.BaseFetchCodeMapping;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BaseFetchCodeMappingListVO extends CodeVO {
    private BaseFetchCodeMapping codes;
}
