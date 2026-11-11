package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (subs_bill)实体类
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("subs_bill")
public class SubsBill extends Model<SubsBill> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 流水号
     */
    private String transNo;

    /**
     * 支付平台交易号
     */
    private String tradeNo;

    /**
     * subscription.id
     */
    private String subscriptionId;

    /**
     * 套餐
     */
    private String planId;
    /**
     * 应付
     */
    private BigDecimal amount;
    /**
     * 优惠减免
     */
    private BigDecimal couponMoney;
    /**
     * 实付
     */
    private BigDecimal netIncome;
    /**
     * 买家类型：brand 、oasis
     */
    private String buyerFidType;
    /**
     * 买家账户id
     */
    private String buyerFid;
    /**
     * 客户付款日期
     */
    private Date buyerPayAt;
    /**
     * 平台打款日期
     */
    private Date sellerGetAt;
    /**
     * 资金托管情况
     */
    private String whereStoreMoney;
    /**
     * 备注
     */
    private String remark;

    /**
     * 缴费频率
     */
    private String billCalendar;

    private String sellerBrandId;
    /**
     * 状态
     */
    private String status;
    /**
     * 有试用期的账单首期支付日期
     */
    private Date unfreezeAt;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}