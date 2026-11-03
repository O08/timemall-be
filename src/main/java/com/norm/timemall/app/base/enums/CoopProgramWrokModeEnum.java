package com.norm.timemall.app.base.enums;


import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum CoopProgramWrokModeEnum {

    FULL_TIME("full_time"),

    PART_TIME("part_time"),

    FLEXIBLE("flexible");

    private final String value;

    CoopProgramWrokModeEnum(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (CoopProgramWrokModeEnum s : CoopProgramWrokModeEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}

