package com.norm.timemall.app.base.enums;

import java.util.Objects;

/**
 * trialing: The subscription is currently in a trial period and you can safely provision your product for your customer. The subscription transitions automatically to active when a customer makes the first payment.
 * active: 	The subscription is in good standing.
 * incomplete: The customer must make a successful payment  to activate the subscription.Subscriptions can also be incomplete if there’s a pending payment and the PaymentIntent status is processing.
 * incomplete_expired: The initial payment on the subscription failed and the customer didn’t make a successful payment of subscription creation. These subscriptions don’t bill customers. This status exists so you can track customers that failed to activate their subscriptions.
 * canceled:The subscription was canceled. During cancellation, automatic collection for all unpaid invoices is disabled .This is a terminal state that can’t be updated
 * unpaid: The latest invoice hasn’t been paid but the subscription remains in place. The latest invoice remains open and invoices continue to generate, but payments aren’t attempted. Revoke access to your product when the subscription is unpaid because payments were already attempted and retried while past_due. To move the subscription to active, pay the most recent invoice before its due date.
 */
public enum SubscriptionStatusEnum {
    ACTIVE("active","正常"),
    TRIALING("trialing","试用中"),
    INCOMPLETE("incomplete","订阅未完成"),
    UNPAID("unpaid","待支付"),
    INCOMPLETE_EXPIRED("incomplete_expired","订阅已过期"),
    CANCELED("canceled","已取消"),
    CLOSED("closed","已关闭")

    ;
    private String mark;
    private String desc;

    SubscriptionStatusEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (SubscriptionStatusEnum s : SubscriptionStatusEnum.values()) {
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
