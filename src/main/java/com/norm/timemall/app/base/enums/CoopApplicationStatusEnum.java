package com.norm.timemall.app.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum CoopApplicationStatusEnum {

    PENDING("pending"),

    APPROVED("approved"),

    REJECTED("rejected")
    ;

    private final String value;

    CoopApplicationStatusEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (CoopApplicationStatusEnum s : CoopApplicationStatusEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}
