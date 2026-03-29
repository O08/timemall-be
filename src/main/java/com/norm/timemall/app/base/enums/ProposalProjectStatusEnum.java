package com.norm.timemall.app.base.enums;

import java.util.Objects;

// 订单状态枚举
public enum ProposalProjectStatusEnum {
    DRAFT, // 未签约草案
    SIGNED, // 已签约
    DELIVERING, // 交付中

    COMPLETED, // 履约完成
    SUSPENDED; // 中止

    public static boolean validation(String value) {
        for (ProposalProjectStatusEnum s : ProposalProjectStatusEnum.values()) {
            if (Objects.equals(s.ordinal()+"", value)) {
                return true;
            }
        }
        return false;
    }

}
