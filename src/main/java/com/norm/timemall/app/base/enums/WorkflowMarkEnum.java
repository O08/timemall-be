package com.norm.timemall.app.base.enums;

public enum WorkflowMarkEnum {
    IN_QUEUE("1","队列中"),
    AUDITING("2","审计中"),
    AUDITED("3","审计完成"),
    STARRED("4","已定稿，履约中"),
    SUSPEND("5","中止"),
    PAUSED("6","停止"),
    FINISH("7","已经完成")
    ;
    private String mark;
    private String desc;

    WorkflowMarkEnum(String mark, String desc) {
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
