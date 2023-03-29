package com.norm.timemall.app.base.enums;

public enum ObjectRecordMarkEnum {
    COOPERATION("1","合作意向"),
    OWNED("2","达成合作"),
    END("3","结束"),
    DENY("4","拒绝")
    ;
    private String mark;
    private String desc;

    ObjectRecordMarkEnum(String mark, String desc) {
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
