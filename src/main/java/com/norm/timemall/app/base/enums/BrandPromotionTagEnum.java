package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum BrandPromotionTagEnum {
    OPEN("1","开启"),
    CLOSED("2","关闭")
    ;
    private String mark;
    private String desc;

    BrandPromotionTagEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 判断值是否满足枚举中的value
     *
     * @param value 映射值
     * @return 判断结果：布尔值
     */
    public static boolean validation(String value) {
        for (BrandPromotionTagEnum s : BrandPromotionTagEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
