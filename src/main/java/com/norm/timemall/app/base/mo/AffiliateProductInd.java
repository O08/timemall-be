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
 * 带货产品基表(affiliate_product_ind)实体类
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("affiliate_product_ind")
public class AffiliateProductInd extends Model<AffiliateProductInd> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brand.id
     */
    private String supplierBrandId;
    /**
     * brand.brand_name
     */
    private String supplierBrandName;
    /**
     * customer.create_at
     */
    private Date supplierCreateAt;
    /**
     * customer.modified_at
     */
    private Date supplierModifiedAt;
    /**
     * brand.blue_end_at
     */
    private Date supplierBlueEndAt;
    /**
     * cell.id
     */
    private String cellId;
    /**
     * cell_plan.id
     */
    private String planId;
    /**
     * cell.title
     */
    private String productName;
    /**
     * plantyp: bird ,value is cell_plan.price
     */
    private BigDecimal planPrice;
    /**
     * cell_plan.create_at
     */
    private Date planCreateAt;
    /**
     * cell.provide_invoice
     */
    private Integer provideInvoice;
    /**
     * revenue share
     */
    private BigDecimal revshare;
    /**
     * 浏览量
     */
    private Integer views;
    /**
     * 特约销量
     */
    private Integer cellSaleVolume;
    /**
     * 单品销量
     */
    private Integer planSaleVolume;
    /**
     * 销售额
     */
    private BigDecimal sales;
    /**
     * 退款订单数量
     */
    private Integer refundOrders;
    /**
     * 关联达人数量
     */
    private Integer influencers;
    /**
     * 产品状态:cell.mark
     */
    private String cellMark;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}