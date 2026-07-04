package com.norm.timemall.app.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum CoopProgramStatusEnum {

    RECRUITING("recruiting"),

    CLOSED("closed"),

    INVALId("invalid")
    ;

    private final String value;

    CoopProgramStatusEnum(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (CoopProgramStatusEnum s : CoopProgramStatusEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}
