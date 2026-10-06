package com.norm.timemall.app.base.enums;


import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum BlvRedeemRewardTypeEnum {

    ONE_MONTH_VIP("one_month_vip"),


    GIFT_POINTS("gift_points");

    private final String value;

    BlvRedeemRewardTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (BlvRedeemRewardTypeEnum s : BlvRedeemRewardTypeEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}

