package com.norm.timemall.app.base.enums;

public enum WithdrawTagEnum {
    CREATED("1","创建"),
    SUCCESS("2","成功"),
    FAIL("3","失败");
    private String mark;
    private String desc;

    WithdrawTagEnum(String mark, String desc) {
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
