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
 * (credit_coupon)实体类
 *
 * @author kancy
 * @since 2024-08-03 10:31:53
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("credit_coupon")
public class CreditCoupon extends Model<CreditCoupon> implements Serializable {
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
     * 可用体验金
     */
    private BigDecimal creditPoint;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}