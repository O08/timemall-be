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
 * (common_order_payment)实体类
 *
 * @author kancy
 * @since 2023-07-18 16:13:30
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("common_order_payment")
public class CommonOrderPayment extends Model<CommonOrderPayment> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 支付状态
     */
    private String status;
    /**
     * 支付状态说明
     */
    private String statusDesc;
    /**
     * 支付类型
     */
    private String payType;
    /**
     * 支付类型说明
     */
    private String payTypeDesc;
    /**
     * 订单号
     */
    private String tradingOrderId;
    /**
     * 订单号类型
     */
    private String tradingOrderType;
    /**
     * 支付平台交易号
     */
    private String tradeNo;
    /**
     * 实付金额
     */
    private BigDecimal totalAmount;
    /**
     * 报文
     */
    private String message;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}