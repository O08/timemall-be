package com.norm.timemall.app.base.enums;

import com.norm.timemall.app.base.exception.ErrorCodeException;

import java.util.Objects;

public enum OasisInvitationLinkExpireTimeTypeEnum {
    ONE_HOUR("1h","1小时"),
    ONE_DAY("1d","1天"),
    SEVEN_DAYS("7d", "7天"),
    ONE_MONTH("1month","1个月"),
    SIX_MONTHS("6month","6个月"),
    ONE_YEAR("1year","1年")
    ;
    private String mark;
    private String desc;

    OasisInvitationLinkExpireTimeTypeEnum(String mark, String desc) {
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

    public static boolean validation(String value) {
        for (OasisInvitationLinkExpireTimeTypeEnum s : OasisInvitationLinkExpireTimeTypeEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }

    public static OasisInvitationLinkExpireTimeTypeEnum findType(String mark) {
        for (OasisInvitationLinkExpireTimeTypeEnum type : values()) {
            if (type.mark.equals(mark)) {
                return type;
            }
        }
        throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
    }

}
