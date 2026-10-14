package com.norm.timemall.app.base.enums;

public enum CellMarkEnum {
    DRAFT("1","草稿"),
    ONLINE("2","上线"),
    OFFLINE("3","下线")
    ;
    private String mark;
    private String desc;

    CellMarkEnum(String mark, String desc) {
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
