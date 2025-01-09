package com.norm.timemall.app.base.enums;

public enum MpsTypeEnum {
    FROM_MILLSTONE("1","millstone order"),
    FROM_TEMPLATE("2","由模版创建商单"),
    FAST("3","快捷商单")
    ;
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
