package com.norm.timemall.app.base.enums;

import java.util.Objects;

/**
 * Mentee status enumeration
 * 1 - 申请 (Application)
 * 2 - 培训 (Training) 
 * 3 - 已毕业 (Graduated)
 */
public enum MenteeStatusEnum {
    APPLICATION("1", "申请"),
    TRAINING("2", "培训"),
    GRADUATED("3", "已毕业");

    private final String code;
    private final String description;

    MenteeStatusEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    /**
     * Get enum by code
     */
    public static MenteeStatusEnum fromCode(String code) {
        for (MenteeStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public static boolean validation(String value) {
        for (MenteeStatusEnum s : MenteeStatusEnum.values()) {
            if (Objects.equals(s.getCode(), value)) {
                return true;
            }
        }
        return false;
    }
}