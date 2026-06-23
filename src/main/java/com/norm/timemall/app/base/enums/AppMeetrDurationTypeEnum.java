package com.norm.timemall.app.base.enums;


import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum AppMeetrDurationTypeEnum {

    MINUTES("Minutes"),

    HOURS("Hours"),

    DAYS("Days"),

    WEEKS("Weeks"),

    MONTHS("Months");

    private final String value;

    AppMeetrDurationTypeEnum(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (AppMeetrDurationTypeEnum s : AppMeetrDurationTypeEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}

