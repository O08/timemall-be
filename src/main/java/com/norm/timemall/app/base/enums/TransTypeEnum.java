package com.norm.timemall.app.base.enums;

public enum TransTypeEnum {
    COMMISSION("1","部落任务佣金"),
    OTHER("2","其他"),

    TRANSFER("3","交易"),
    TOPUP_OASIS("4","部落赞助"),
    OASIS_COLLECT_IN("5","部落收账"),
    TOPUP_MPS_FUND("6","合约基金充值"),
    MPS_FUND_TRANSFER("7","商单打款"),
    PLAN_ORDER_TRANSFER_TO_BRAND("8","平台单品打款"),
    PLAN_ORDER_PAY("9","单品付款"),
    REFUND("10","退款"),
    MILLSTONE_BILL_PAY("11","特约账单支付"),
    MILLSTONE_AFFILIATE_PAY("12","特约账单佣金"),
    PLAN_ORDER_AFFILIATE_PAY("13", "单品佣金"),
    PPC_BILL("15","ppc 打款"),
    OASIS_ADMIN_WITHDRAW("16","部落管理员取出资金"),
    VIRTUAL_PRODUCT_ORDER_PAY("17","虚拟商品付款"),
    VIRTUAL_REMITTANCE_TO_SELLER("18","虚拟商品平台汇款到卖家"),

    PROPOSAL_BILL_PAY("19", "提案账单支付");
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
