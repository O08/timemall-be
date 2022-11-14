package com.norm.timemall.app.base.enums;

public enum EmailMessageTopicEnum {
    EMAIL_VERIFICATION_CODE("email_verified_code","邮箱验证码"),
    EMAIL_PASSWORD_RESET("email_password_reset","密码重置" );
    private String topic;
    private String desc;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    EmailMessageTopicEnum(String topic, String desc) {
        this.topic = topic;
        this.desc = desc;
    }
}
