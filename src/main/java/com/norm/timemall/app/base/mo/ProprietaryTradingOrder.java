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
 * (proprietary_trading_order)实体类
 *
 * @author kancy
 * @since 2023-02-04 16:06:43
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("proprietary_trading_order")
public class ProprietaryTradingOrder extends Model<ProprietaryTradingOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 用户id
     */
    private String customerId;
    /**
     * 自营物品id
     */
    private String tradingId;
    /**
     * 品牌id
     */
    private String brandId;
    /**
     * total
     */
    private BigDecimal total;
    /**
     * quantity
     */
    private Integer quantity;
    /**
     * 付费单位
     */
    private String sbu;
    /**
     * 订单状态
     */
    private String status;
    /**
     * 订单状态说明
     */
    private String statusDesc;
    /**
     * cancelled
     */
    private Integer cancelled;
    /**
     * cancelledReason
     */
    private String cancelledReason;
    /**
     * cancelledAt
     */
    private Date cancelledAt;
    /**
     * refunded
     */
    private Integer refunded;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}