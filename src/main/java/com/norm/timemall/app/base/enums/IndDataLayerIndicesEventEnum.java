package com.norm.timemall.app.base.enums;

public enum IndDataLayerIndicesEventEnum {
    IMPRESSIONS("impressions","曝光"),
    CLICKS("clicks","点击"),
    APPOINTMENTS("appointments","特约预约"),
    PURCHASES("purchases","单品购买")
    ;
    private String mark;
    private String desc;

    IndDataLayerIndicesEventEnum(String mark, String desc) {
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
