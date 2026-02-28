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
 * (bill)实体类
 *
 * @author kancy
 * @since 2022-10-27 11:26:23
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("bill")
public class Bill extends Model<Bill> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     *  业务类型： cell or proposal
     */

    private String categories;
    /**
     * 收费项目
     */
    private String stage;

    /**
     * 收费序列
     */
    private String stageNo;
    /**
     * amount
     */
    private BigDecimal amount;
    /**
     * voucher
     */
    private String voucher;
    /**
     * 账单状态标记
     */
    private String mark;
    /**
     * orderId
     */
    private String orderId;
    /**
     * 扣除减免、佣金收入
     */
    private BigDecimal netIncome;
    /**
     * 佣金
     */
    private BigDecimal commission;
    /**
     * 优惠减免
     */
    private BigDecimal promotionDeduction;

    private String payeeFid;

    private String payeeFidType;

    private String payerFid;

    private String payerFidType;

    private String remark;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}