package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppCardGuidePostStrategyEnum {
    ALL("1","全面开放"),
    ONLY_ADMIN("2","仅仅管理员");
    private String mark;
    private String desc;

    AppCardGuidePostStrategyEnum(String mark, String desc) {
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
        for (AppCardGuidePostStrategyEnum s : AppCardGuidePostStrategyEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
