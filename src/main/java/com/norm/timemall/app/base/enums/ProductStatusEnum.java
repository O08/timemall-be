package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum ProductStatusEnum {
    DRAFT("1","草稿"),
    ONLINE("2","上线"),
    OFFLINE("3","下线")
    ;
    private String mark;
    private String desc;

    ProductStatusEnum(String mark, String desc) {
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
        for (ProductStatusEnum s : ProductStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
