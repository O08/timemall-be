package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum MembershipCardTypeEnum {
    MONTHLY("monthly","月卡"),
    YEARLY("yearly","年卡")
    ;
    private String mark;
    private String desc;

    MembershipCardTypeEnum(String mark, String desc) {
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

    public static boolean validation(String value) {
        for (MembershipCardTypeEnum s : MembershipCardTypeEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
