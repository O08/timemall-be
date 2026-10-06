package com.norm.timemall.app.base.enums;

/**
 * group_member tb policy_rel enum
 */
public enum GroupMemberPolicyRelEnum {
    READ("read","只读"),
    READ_WRITE("read_write","读写"),
    ;
    private String mark;
    private String desc;

    GroupMemberPolicyRelEnum(String mark, String desc) {
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
