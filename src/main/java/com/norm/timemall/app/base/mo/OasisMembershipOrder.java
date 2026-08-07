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
 * (oasis_membership_order)实体类
 *
 * @author kancy
 * @since 2025-10-29 15:52:41
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_membership_order")
public class OasisMembershipOrder extends Model<OasisMembershipOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 订单号
     */
    private String orderNo;
    /**
     * 支付凭证
     */
    private String tradeNo;
    /**
     * 买家
     */
    private String buyerBrandId;
    /**
     * 商家
     */
    private String sellerBrandId;
    /**
     * 套餐
     */
    private String tierId;
    /**
     * 金额
     */
    private BigDecimal total;
    /**
     * 卡种：年卡/月卡
     */
    private String cardType;

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