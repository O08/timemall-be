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
 * 带货订单表(affiliate_order)实体类
 *
 * @author kancy
 * @since 2024-06-08 09:57:35
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("affiliate_order")
public class AffiliateOrder extends Model<AffiliateOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 佣金订单类型：特约、单品
     */
    private String orderType;
    /**
     * 订单Id：cell_plan_order.id or order_detial.id
     */
    private String orderId;
    /**
     * 带货人brand id
     */
    private String influencer;
    /**
     * 渠道id
     */
    private String outreachChannelId;
    /**
     * 佣金比例
     */
    private BigDecimal revshare;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 分销方式： api or 通用
     */
    private String market;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}