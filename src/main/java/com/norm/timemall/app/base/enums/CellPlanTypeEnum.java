package com.norm.timemall.app.base.enums;

public enum CellPlanTypeEnum {
    BIRD("bird","basic plan"),
    EAGLE("eagle","standard plan"),
    ALBATROSS("albatross", "premium plan");
    private String mark;
    private String desc;

    CellPlanTypeEnum(String mark, String desc) {
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
