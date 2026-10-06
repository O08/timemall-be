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
 * 渠道榜(affiliate_hot_outreach)实体类
 *
 * @author kancy
 * @since 2024-06-05 10:46:51
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("affiliate_hot_outreach")
public class AffiliateHotOutreach extends Model<AffiliateHotOutreach> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 时间跨度：昨天、本周、当月
     */
    private String timespan;
    /**
     * brand.id
     */
    private String brandId;
    /**
     * affiliate_outreach_channel.id
     */
    private String outreachChannelId;
    /**
     * 特约销量
     */
    private Integer cellSaleVolume;
    /**
     * 单品销量
     */
    private Integer planSaleVolume;
    /**
     * 浏览量
     */
    private Integer views;
    /**
     * 销售额
     */
    private BigDecimal sales;
    /**
     * 退款订单数量
     */
    private Integer refundOrders;
    /**
     * 已结算收款
     */
    private BigDecimal settledPayment;
    /**
     * 待结算收款
     */
    private BigDecimal unsettledPayment;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}