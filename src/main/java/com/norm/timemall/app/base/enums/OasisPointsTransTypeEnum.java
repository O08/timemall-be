package com.norm.timemall.app.base.enums;

public enum OasisPointsTransTypeEnum {

    APP_REDEEM_ORDER_PAY("1", "兑换中心兑换物品"),
    APP_REDEEM_REFUND("2","兑换中心退款")
    ;
    private String mark;
    private String desc;

    OasisPointsTransTypeEnum(String mark, String desc) {
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
}
