package com.norm.timemall.app.base.enums;

public enum OasisCommissionTagEnum {
    CREATED("1","创建成功，等待审核"),
    ACCEPT("2","接受任务"),
    DENY("3","拒绝任务"),
    FINISH("4","完成任务"),
    ABOLISH("5","审核不通过，废除任务"),
    ADD_TO_NEED_POOL("6","审核通过，进入需求池"),
    FIND_NEW_SUPPLIER("7","重新匹配新的服务商")
    ;
    private String mark;
    private String desc;

    OasisCommissionTagEnum(String mark, String desc) {
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
