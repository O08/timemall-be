package com.norm.timemall.app.base.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

public enum AppMeetrEventCategoryEnum {

    TECHNOLOGY("1", "Technology"),

    SOCIAL_ACTIVITY("2", "Social Activity"),

    HOBBIES_PASSIONS("3", "Hobbies & Passions"),

    SPORTS_FITNESS("4", "Sports & Fitness"),

    TRAVEL_OUTDOOR("5", "Travel & Outdoor"),

    CAREER_BUSINESS("6", "Career & Business"),

    GAMES("7", "Games"),

    DANCING("8", "Dancing"),

    MUSIC("9", "Music"),

    HEALTH_WELLBEING("10", "Health & Wellbeing"),

    ART_CULTURE("11", "Art & Culture"),

    SCIENCE_EDUCATION("12", "Science & Education"),

    PETS_ANIMALS("13", "Pets & Animals"),

    WRITING("14", "Writing"),

    PARENTS_FAMILY("15", "Parents & Family");

    private final String code;
    private final String description;

    AppMeetrEventCategoryEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    @JsonValue
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static AppMeetrEventCategoryEnum fromCode(String code) {
        for (AppMeetrEventCategoryEnum category : AppMeetrEventCategoryEnum.values()) {
            if (category.getCode().equals(code)) {
                return category;
            }
        }
        return null;
    }
    public static boolean validation(String value) {
        for (AppMeetrEventCategoryEnum s : AppMeetrEventCategoryEnum.values()) {
            if (Objects.equals(s.getCode(), value)) {
                return true;
            }
        }
        return false;
    }
}
