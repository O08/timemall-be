package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppViberPostEmbedFacetEnum {
    ATTACHMENT("attachment", "附件"),
    IMAGE("image", "图片"),
    THIRD_PARTY_IMAGE("third_party_image", "第三方图片资源"),
    VIDEO("video", "视频"),
    LINK("link", "统一链接"),
    ;

    private String code;
    private String desc;

    AppViberPostEmbedFacetEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (AppViberPostEmbedFacetEnum s : AppViberPostEmbedFacetEnum.values()) {
            if (Objects.equals(s.getCode(), value)) {
                return true;
            }
        }
        return false;
    }
}