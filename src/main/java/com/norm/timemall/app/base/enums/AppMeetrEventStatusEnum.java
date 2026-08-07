package com.norm.timemall.app.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum AppMeetrEventStatusEnum {

    PREPARING("Preparing"),

    CANCELLED("Cancelled");

    private final String value;

    AppMeetrEventStatusEnum(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (AppMeetrEventStatusEnum s : AppMeetrEventStatusEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}
