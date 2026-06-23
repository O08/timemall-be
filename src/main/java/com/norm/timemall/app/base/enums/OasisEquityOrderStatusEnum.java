package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum OasisEquityOrderStatusEnum {
    HOLDING("1","持有"),
    REDEEMED("2","已收回"),
    WRITE_OFF("3","已核销"),
    BUY_PENDING("4","正在处理支付操作"),
    REDEEM_PENDING("5","正在处理回收操作"),
    WRITE_OFF_PENDING("6","正在处理核销操作"),


    ;
    private String mark;
    private String desc;

    OasisEquityOrderStatusEnum(String mark, String desc) {
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
        for (OasisEquityOrderStatusEnum s : OasisEquityOrderStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
