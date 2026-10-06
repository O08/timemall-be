package com.norm.timemall.app.base.enums;

public enum MarketPuzzleEventTagEnum {
    CREATED("1","创建"),
    ALREADY_OPEN_BOX("2","已发放奖励"),

    END("3","事件结束")
    ;
    private String mark;
    private String desc;

    MarketPuzzleEventTagEnum(String mark, String desc) {
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
