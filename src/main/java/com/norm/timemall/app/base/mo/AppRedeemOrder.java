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
 * (app_redeem_order)实体类
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_redeem_order")
public class AppRedeemOrder extends Model<AppRedeemOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    private String orderNo;
    /**
     * channel id
     */
    private String oasisChannelId;
    /**
     * 产品
     */
    private String productId;
    /**
     * 发货方式
     */
    private String shippingType;
    /**
     * 商家
     */
    private String sellerBrandId;
    /**
     * 买家
     */
    private String buyerBrandId;
    /**
     * 收货地址
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingAddress;
    /**
     * 收货邮箱
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingEmail;

    /**
     * 收货人
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String consignee;

    /**
     * 交货信息
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String deliveryNote;
    /**
     * 交付文件
     */
    private String deliveryMaterial;

    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 购买数量
     */
    private Integer quantity;
    /**
     * 总计
     */
    private BigDecimal total;

    /**
     * 退款标识
     */
    private String refunded;
    /**
     * 退款标识
     */
    private String alreadyPay;

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