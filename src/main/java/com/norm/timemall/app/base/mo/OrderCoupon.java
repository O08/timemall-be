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
 * (order_coupon)实体类
 *
 * @author kancy
 * @since 2024-08-03 10:31:53
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_coupon")
public class OrderCoupon extends Model<OrderCoupon> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 优惠券发放者品牌id
     */
    private String supplierBrandId;
    /**
     * 消费者品牌id
     */
    private String consumerBrandId;
    /**
     * 特约id
     */
    private String cellId;
    /**
     * 订单类型：特约、单品
     */
    private String orderType;
    /**
     * 订单id
     */
    private String orderId;
    /**
     *  体验金抵扣金额
     */
    private BigDecimal creditPoint;
    /**
     * 尝鲜折扣
     */
    private Integer earlyBirdDiscount;
    /**
     * 复购折扣
     */
    private Integer repurchaseDiscount;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}