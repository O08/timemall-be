package com.norm.timemall.app.base.enums;


import java.util.Objects;

/**
 * 专项，优惠活动限定特定的商品或者套餐
 */
public enum SubsOfferTypeEnum {

    PAY_QUARTERLY_DISCOUNT_COUPON_SP("pay_quarterly_discount_coupon_sp","年付折扣券（专项）"),
    PAY_YEARLY_DISCOUNT_COUPON_SP("pay_yearly_discount_coupon_sp","年付折扣券（专项）"),
    FIRST_PERIOD_DISCOUNT_PROMO_CODE_SP("first_period_discount_promo_code_sp","首期折扣优惠码（专项）"),

    FIRST_PERIOD_CASH_PROMO_CODE_SP("first_period_cash_promo_code_sp","首期现金减免优惠码（专项）"),
    FULL_ITEM_DISCOUNT_PROMO_CODE("full_item_discount_promo_code","全店折扣优惠码");
    private String mark;
    private String desc;

    SubsOfferTypeEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }
    public static boolean validation(String value) {
        for (SubsOfferTypeEnum s : SubsOfferTypeEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
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
