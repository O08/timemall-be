package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppViberPostFileStautsEnum {
    UNUSED("0", "待使用"),
    IN_SERVICE("1", "服役中"),
    DELETED("2", "已删除");

    private String code;
    private String desc;

    AppViberPostFileStautsEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean validation(Integer value) {
        for (AppViberPostFileStautsEnum s : AppViberPostFileStautsEnum.values()) {
            if (Objects.equals(s.getCode(), value)) {
                return true;
            }
        }
        return false;
    }
}