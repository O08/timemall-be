package com.norm.timemall.app.base.enums;

/**
 * group_member tb policy_rel enum
 */
public enum CommissionWsRoleEnum {
    VIEWER("viewer","观光客"),
    SUPPLIER("supplier","服务商"),
    ADMIN("admin","管理")
    ;
    private String mark;
    private String desc;

    CommissionWsRoleEnum(String mark, String desc) {
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
