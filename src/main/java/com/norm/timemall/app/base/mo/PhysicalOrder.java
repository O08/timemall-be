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
 * 实物主订单表(physical_order)实体类
 *
 * @author kancy
 * @since 2026-10-26 15:37:54
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("physical_order")
public class PhysicalOrder extends Model<PhysicalOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 买家
     */
    private String buyerBrandId;
    /**
     * 卖家
     */
    private String sellerBrandId;
    /**
     * 商品原价总总额 (所有子单单价*数量之和)
     */
    private BigDecimal totalAmount;
    /**
     * 全单营销优惠总金额 (满减、优惠券等叠加)
     */
    private BigDecimal promotionAmount;
    /**
     * 实际应付/实付总金额 (用户合并支付调起的总价)
     */
    private BigDecimal payAmount;
    /**
     * 收货人姓名
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String recipient;
    /**
     * 收货人联系电话
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String recipientPhone;
    /**
     * 详细收货地址
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingAddress;
    /**
     * 物流配送方式 (如:同城配送、顺丰、自提)
     */
    private String shippingMethod;
    /**
     * 物流单号/快递单号
     */
    private String shippingNumber;
    /**
     * 发货备注/包裹留言
     */
    private String shippingNote;
    /**
     * 物流履约状态
     */
    private String shippingStatus;
    /**
     * 支付状态
     */
    private String paymentStatus;
    /**
     * 配送员/骑手/快递员联系电话
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String courierPhone;
    /**
     * 订单主状态
     */
    private String orderStatus;
    /**
     * 下单时间
     */
    private Date createAt;
    /**
     * 最后更新时间
     */
    private Date modifiedAt;

}