package com.norm.timemall.app.base.enums;

public enum TransTypeEnum {
    COMMISSION("1","佣金"),
    OTHER("2","其他"),

    TRANSFER("3","交易"),
    TOPUP_OASIS("4","充值Oasis"),
    OASIS_COLLECT_IN("5","oasis 收账"),
    TOPUP_MPS_FUND("6","转帐到MPS fund"),
    MPS_FUND_TRANSFER("7","合约基金支付转账"),
    PLAN_ORDER_TRANSFER_TO_BRAND("8","平台单品打款"),
    PLAN_ORDER_PAY("9","单品付款"),
    REFUND("10","退款"),
    MILLSTONE_BILL_PAY("11","特约账单支付"),
    MILLSTONE_AFFILIATE_PAY("12","特约账单佣金"),
    PLAN_ORDER_AFFILIATE_PAY("13", "单品佣金"),
    PPC_BILL("15","ppc 打款")
    ;
    private String mark;
    private String desc;

    TransTypeEnum(String mark, String desc) {
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
}
