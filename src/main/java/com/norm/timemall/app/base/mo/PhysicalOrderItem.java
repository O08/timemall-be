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
 * 实物订单商品明细表(physical_order_item)实体类
 *
 * @author kancy
 * @since 2026-10-26 15:37:54
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("physical_order_item")
public class PhysicalOrderItem extends Model<PhysicalOrderItem> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 关联的主订单号
     */
    private String orderId;
    /**
     * 关联的商品ID
     */
    private String productId;
    /**
     * 购买时的商品名称快照
     */
    private String productName;
    /**
     * 购买时的商品图片快照
     */
    private String productThumbnail;
    /**
     * 商品购买单价快照
     */
    private BigDecimal productPrice;
    /**
     * 购买数量
     */
    private Integer quantity;
    /**
     * 分摊到该商品的促销优惠金额
     */
    private BigDecimal splitPromotionAmount;
    /**
     * 该商品优惠后的实付净额
     */
    private BigDecimal splitPayAmount;
    /**
     * 买家填写或商家标记的退款/缺货原因详细文字描述
     */
    private String refundReason;
    /**
     * 该商品实际已发生的退款金额
     */
    private BigDecimal actualRefundAmount;
    /**
     * 状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 最后更新时间
     */
    private Date modifiedAt;

}