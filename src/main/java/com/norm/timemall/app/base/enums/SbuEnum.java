package com.norm.timemall.app.base.enums;


import java.util.Objects;

public enum SbuEnum {
    YEAR("year","year"),
    QUARTER("quarter","quarter"),
    MONTH("month","month"),
    DAY("day","day"),
    WEEK("week","week"),
    HOUR("hour","hour"),
    MINUTE("minute","minute"),
    SECOND("second","second");
    private String label;
    private String value;
    SbuEnum(String label, String value) {
        this.label = label;
        this.value = value;
    }
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    /**
     * 判断值是否满足枚举中的value
     *
     * @param value 映射值
     * @return 判断结果：布尔值
     */
    public static boolean validation(String value) {
        for (SbuEnum s : SbuEnum.values()) {
            if (Objects.equals(s.getValue(), value)) {
                return true;
            }
        }
        return false;
    }


}
