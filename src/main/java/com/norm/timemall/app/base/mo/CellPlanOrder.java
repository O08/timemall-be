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
 * (cell_plan_order)实体类
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cell_plan_order")
public class CellPlanOrder extends Model<CellPlanOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * cellId
     */
    private String cellId;
    /**
     * cell_plan tb id
     */
    private String planId;
    /**
     * 单品类型：小鸟，老鹰，信天翁
     */
    private String planType;
    /**
     * 单品类型
     */
    private String planTypeDesc;
    /**
     * 单品 名称
     */
    private String planTitle;
    /**
     * 单品 内容
     */
    private String planContent;
    /**
     * 单品 功能
     */
    private String planFeature;
    /**
     * 单品 售价
     */
    private BigDecimal planPrice;
    /**
     * mps life cycle
     */
    private String tag;

    /**
     * 服务商
     */
    private String brandId;

    /**
     * 客户id
     */
    private String consumerId;

    /**
     * 扣除佣金收入
     */
    private BigDecimal netIncome;
    /**
     * 佣金
     */
    private BigDecimal commission;
    /**
     * 促销抵扣
     */
    private BigDecimal promotionDeduction;
    /**
     * 实际收款
     */
    private BigDecimal revenue;

    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}