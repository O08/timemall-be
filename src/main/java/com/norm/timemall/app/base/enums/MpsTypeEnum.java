package com.norm.timemall.app.base.enums;

public enum MpsTypeEnum {
    FROM_MILLSTONE("1","millstone order"),
    FROM_PLAN("2","自建");
    private String mark;
    private String desc;

    MpsTypeEnum(String mark, String desc) {
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
