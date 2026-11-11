package com.norm.timemall.app.base.enums;

public enum EmailNoticeEnum {
    CELL_ORDER_RECEIVING("cell_order_receiving"," cell order receiving email notice"),
    CELL_PLAN_ORDER_RECEIVING("cell_plan_order_receiving","cell plan order receiving email notice");
    private String mark;
    private String desc;

    EmailNoticeEnum(String mark, String desc) {
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
