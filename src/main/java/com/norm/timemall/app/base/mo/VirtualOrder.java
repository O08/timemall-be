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
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (virtual_order)实体类
 *
 * @author kancy
 * @since 2025-04-29 11:09:58
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("virtual_order")
public class VirtualOrder extends Model<VirtualOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 订单编号
     */
    private String orderNo;
    /**
     * 商品
     */
    private String productId;
    /**
     * 卖家
     */
    private String sellerBrandId;
    /**
     * 买家
     */
    private String buyerBrandId;
    /**
     * 单价
     */
    private BigDecimal productPrice;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * 费用
     */
    private BigDecimal totalFee;
    /**
     * 状态
     */
    private String tag;

    /**
     * 订单付款标识：1 已付款
     */
    private String alreadyPay;

    /**
     * 平台打款标识：1 已打款
     */
    private String alreadyRemittance;

    /**
     * 退款标识：1 已退款
     */
    private String alreadyRefund;


    /**
     * 退款原因
     */
    private String refundReason;

    /**
     * 货品
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String pack;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}