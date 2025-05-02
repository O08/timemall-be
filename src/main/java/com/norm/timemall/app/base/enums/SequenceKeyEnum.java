package com.norm.timemall.app.base.enums;

public enum SequenceKeyEnum {
    PUZZLE_VERSION_ONE("puzzle_version_one","解密第一期"),
    DSP_CASE_NO("dsp_case_no","举报编号"),
    VIRTUAL_PRODUCT_ORDER_NO("virtual_order_no","虚拟商品订单编号")
    ;
    private String mark;
    private String desc;

    SequenceKeyEnum(String mark, String desc) {
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
