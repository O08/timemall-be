package com.norm.timemall.app.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum FlierStatusEnum {

    NORMAL("normal"),

    FREEZE("freeze");

    private final String value;

    FlierStatusEnum(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (FlierStatusEnum s : FlierStatusEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}
