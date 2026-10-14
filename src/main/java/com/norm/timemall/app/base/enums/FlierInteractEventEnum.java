package com.norm.timemall.app.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum FlierInteractEventEnum {

    RECEIVER_LIKE("receiver_like"),

    RECEIVER_CTA_CLICK("receiver_cta_click"),
    VISITOR_LIKE("visitor_like"),

    VISITOR_CTA_CLICK("visitor_cta_click"),

    ;

    private final String value;

    FlierInteractEventEnum(String value) {
        this.value = value;
    }
    @JsonValue
    public String getValue() {
        return value;
    }

    public static boolean validation(String value) {
        for (FlierInteractEventEnum s : FlierInteractEventEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
}
