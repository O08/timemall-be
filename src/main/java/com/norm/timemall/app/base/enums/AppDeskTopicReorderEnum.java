package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppDeskTopicReorderEnum {
    UP("up","上移"),
    DOWN("down","下移")
    ;
    private String mark;
    private String desc;

    AppDeskTopicReorderEnum(String mark, String desc) {
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
        for (AppDeskTopicReorderEnum s : AppDeskTopicReorderEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
