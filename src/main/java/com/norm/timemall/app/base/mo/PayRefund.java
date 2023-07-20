package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (pay_refund)实体类
 *
 * @author kancy
 * @since 2023-07-19 10:55:03
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_refund")
public class PayRefund extends Model<PayRefund> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 支付平台交易号
     */
    private String tradeNo;
    /**
     * 订单号
     */
    private String tradingOrderId;
    /**
     * 报文
     */
    private String message;
    /**
     * 退款状态
     */
    private String status;
    /**
     * 退款状态说明
     */
    private String statusDesc;
    /**
     * 退款支付平台交易号
     */
    private String refundTradeNo;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}