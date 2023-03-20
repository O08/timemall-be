package com.norm.timemall.app.base.enums;

public enum ObjectRecordMarkEnum {
    COOPERATION("1","合作意向"),
    OWNED("2","拥有"),
    END("3","结束")
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
