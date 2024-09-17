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
 * (pay_transfer)实体类
 *
 * @author kancy
 * @since 2023-07-19 10:55:04
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pay_transfer")
public class PayTransfer extends Model<PayTransfer> implements Serializable {
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
     * 转账状态
     */
    private String status;
    /**
     * 转账状态说明
     */
    private String statusDesc;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}