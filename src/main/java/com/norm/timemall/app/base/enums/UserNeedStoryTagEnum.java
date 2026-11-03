package com.norm.timemall.app.base.enums;

public enum UserNeedStoryTagEnum {

    CREATED("created","创建"),
    PROCESSING("processing","处理中"),
    END("end","处理结束")
            ;
    private String mark;
    private String desc;

    UserNeedStoryTagEnum(String mark, String desc) {
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
