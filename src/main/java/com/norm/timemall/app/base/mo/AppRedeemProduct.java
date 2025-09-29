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
 * (app_redeem_product)实体类
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_redeem_product")
public class AppRedeemProduct extends Model<AppRedeemProduct> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * channel id
     */
    private String oasisChannelId;
    /**
     * 主图
     */
    private String thumbnail;
    /**
     * 商品编号
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 库存
     */
    private Integer inventory;
    /**
     * 限购策略
     */
    private String salesQuotaType;
    /**
     * 限购
     */
    private Integer salesQuota;
    /**
     * 开售日期
     */
    private Date releaseAt;
    /**
     * 首批交货日期
     */
    private Date estimatedDeliveryAt;
    /**
     * 品类
     */
    private String genreId;
    /**
     * 发货方式
     */
    private String shippingType;
    /**
     * 销量
     */
    private Integer soldOrders;
    /**
     * 热度
     */
    private Integer views;
    /**
     * 权益须知
     */
    private String shippingTerm;
    /**
     * 温馨提示
     */
    private String warmReminder;
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