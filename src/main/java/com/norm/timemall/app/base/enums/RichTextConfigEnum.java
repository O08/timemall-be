package com.norm.timemall.app.base.enums;

import lombok.Setter;


public enum RichTextConfigEnum {
    // 邮箱验证码模板
    EMAIL_VERIFICATION_CODE("emailHtml","qrcode"),
    // email 密码重置模板
    EMAIL_PASSWORD_RESET("emailHtml","password_reset"),
    //密码更新成功提示模板
    EMAIL_PASSWORD_UPDATED("emailHtml","password_updated")

    ;
    // template type : email or html or sms
    private String type;
    // template no
    private String no;

    RichTextConfigEnum(String type, String no) {
        this.type = type;
        this.no = no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
