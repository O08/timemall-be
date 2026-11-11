package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppRedeemShippingTypeEnum {
    LOGISTICS("logistics","物流发货"),
    EMAIL("email","邮箱发货"),
    OTHERS("others","其他方式")
    ;
    private String mark;
    private String desc;

    AppRedeemShippingTypeEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (AppRedeemShippingTypeEnum s : AppRedeemShippingTypeEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
