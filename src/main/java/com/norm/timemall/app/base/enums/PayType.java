package com.norm.timemall.app.base.enums;

public enum PayType  implements Code{
    ALIPAY(1, "支付保网页支付");
    private int code;
    private String desc;

    PayType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }
}
