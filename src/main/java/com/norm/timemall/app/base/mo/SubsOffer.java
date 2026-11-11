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
 * (subs_offer)实体类
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("subs_offer")
public class SubsOffer extends Model<SubsOffer> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 券名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private String offerType;
    /**
     * 优惠领取方式：auto,event,kol
     */
    private String claimChannel;
    /**
     * 规则
     */
    private String terms;
    /**
     * 优惠期限
     */
    private Integer durationCycle;
    /**
     * 有效期
     */
    private Integer validDays;
    /**
     * 优惠码
     */
    private String promoCode;
    /**
     * 优惠金额
     */
    private BigDecimal discountAmount;
    /**
     * 折扣
     */
    private Integer discountPercentage;
    /**
     * 单品限用
     */
    private String forProductId;
    /**
     * 套餐限用
     */
    private String forPlanId;
    /**
     * 商家
     */
    private String sellerBrandId;
    /**
     * 券总数量
     */
    private Integer capacity;
    /**
     * 已领取数量
     */
    private Integer claims;
    /**
     * 已使用数量
     */
    private Integer used;
    /**
     * 状态
     */
    private String status;

    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}