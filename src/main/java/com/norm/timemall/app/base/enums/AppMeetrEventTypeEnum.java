package com.norm.timemall.app.base.enums;


import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum AppMeetrEventTypeEnum {

    ONLINE("Online"),

    OFFLINE("Offline"),

    HYBRID("Hybrid");

    private final String value;

    AppMeetrEventTypeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (AppMeetrEventTypeEnum s : AppMeetrEventTypeEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}

