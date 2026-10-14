package com.norm.timemall.app.pod.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.pod.domain.ro.FetchBillDetailRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchBillDetailVO extends CodeVO {
    private FetchBillDetailRO detail;
}
