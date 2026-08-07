package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppFbFeedCommentFeatureTagEnum {
    OFF("0","关闭评论"),
    ON("1","开启评论");
    private String mark;
    private String desc;

    AppFbFeedCommentFeatureTagEnum(String mark, String desc) {
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
        for (AppFbFeedCommentFeatureTagEnum s : AppFbFeedCommentFeatureTagEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
