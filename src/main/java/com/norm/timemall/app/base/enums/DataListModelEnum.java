package com.norm.timemall.app.base.enums;

public enum DataListModelEnum {
    USER_INTEREST("user_interest"," compute user rights and interests"),
    USER_DEL_PRE_REQUIREMENT("user_del_pre_requirement","compute user pre requirement when del");
    private String mark;
    private String desc;

    DataListModelEnum(String mark, String desc) {
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
