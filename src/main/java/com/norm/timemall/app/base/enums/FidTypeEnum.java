package com.norm.timemall.app.base.enums;

public enum FidTypeEnum {
    BRAND("1","Brand"),
    OASIS("2","Oasis");
    private String mark;
    private String desc;

    FidTypeEnum(String mark, String desc) {
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
