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
 * (seller_dashboard)实体类
 *
 * @author kancy
 * @since 2025-06-18 20:46:54
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("seller_dashboard")
public class SellerDashboard extends Model<SellerDashboard> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 品牌
     */
    private String sellerBrandId;
    /**
     * 账户余额
     */
    private BigDecimal balance;

    /**
     * 财富等级
     */
    private Integer wealthLevel;
    /**
     * 员工人数
     */
    private Integer employees;

    /**
     * 半年内被举报案件数量
     */
    private Integer dspCasesSixMonth;

    /**
     * 本月已赚
     */
    private BigDecimal earnedMonth;
    /**
     * 待结算资金-单品
     */
    private BigDecimal pendingPlanPayment;
    /**
     * 待结算资金-虚拟
     */
    private BigDecimal pendingVirtualPayment;
    /**
     * 累积收入
     */
    private BigDecimal earningsToDate;
    /**
     * 累积支出
     */
    private BigDecimal expensesToDate;
    /**
     * 复购收入-单品
     */
    private BigDecimal planEarningsFromRepeatBuyers;
    /**
     * 复购收入-特约
     */
    private BigDecimal cellEarningsFromRepeatBuyers;
    /**
     * 复购收入-虚拟
     */
    private BigDecimal virtualEarningsFromRepeatBuyers;
    /**
     * 回头客-单品
     */
    private Long planRepeatBuyers;
    /**
     * 回头客-特约
     */
    private Long cellRepeatBuyers;
    /**
     * 回头客-虚拟
     */
    private Long virtualRepeatBuyers;
    /**
     * 客户数量-单品
     */
    private Long planTotalBuyers;
    /**
     * 客户数量-特约
     */
    private Long cellTotalBuyers;
    /**
     * 客户数量-虚拟
     */
    private Long virtualTotalBuyers;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}