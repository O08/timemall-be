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
 * (proprietary_trading_payment)实体类
 *
 * @author kancy
 * @since 2023-02-04 16:06:43
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("proprietary_trading_payment")
public class ProprietaryTradingPayment extends Model<ProprietaryTradingPayment> implements Serializable {
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
     * 支付状态说明
     */
    private String tradingOrderId;
    /**
     * 支付报文
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