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
 * 橱窗表(affiliate_influencer_product)实体类
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("affiliate_influencer_product")
public class AffiliateInfluencerProduct extends Model<AffiliateInfluencerProduct> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * cell.id
     */
    private String cellId;
    /**
     * cell_plan.id
     */
    private String planId;
    /**
     * brand.id
     */
    private String supplierBrandId;
    /**
     * brand.id
     */
    private String brandId;
    /**
     * plantyp: bird ,value is cell_plan.price
     */
    private BigDecimal planPrice;
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
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;
    private String supplierAvatar;
    private String cellCover;

}