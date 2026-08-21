package com.norm.timemall.app.base.enums;

public enum ElectricityBusinessTypeEnum {
    TOP_UP("top_up","top up"),

    VIP_GIFT("vip_gift","vip gift"),
    READ_FLIER_BONUS("flier_bonus","read flier bonus"),

    DEDUCT_ELECTRICITY_FOR_BID_MPS("mps_bid_deduct","deduct"),
    DEDUCT_ELECTRICITY_FOR_POST_PROGRAM("post_pgm_deduct","post cooperation program deduct"),

    DEDUCT_ELECTRICITY_FOR_HANDOUT_FLIER("post_flier_deduct","post flier deduct"),

    DEDUCT_ELECTRICITY_FOR_INSTALL_MINI_PROGRAM("install_mi_pgm_deduct","install mini program deduct"),

    ;
    private String mark;
    private String desc;

    ElectricityBusinessTypeEnum(String mark, String desc) {
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
