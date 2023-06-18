package com.norm.timemall.app.base.enums;

public enum CommercialPaperTagEnum {
    CREATED("1","创建"),
    PUBLISH("2","上线交易"),
    OFFLINE("3","下线交易"),
    END("4","完成"),
    DELIVERING("5","交付中")
    ;
    private String mark;
    private String desc;

    CommercialPaperTagEnum(String mark, String desc) {
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
