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
 * (app_redeem_dashboard)实体类
 *
 * @author kancy
 * @since 2025-09-25 10:31:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_redeem_dashboard")
public class AppRedeemDashboard extends Model<AppRedeemDashboard> implements Serializable {
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
     * 销量
     */
    private Integer soldOrders;
    /**
     * 待交付
     */
    private Integer shippingOrders;
    /**
     * 用户
     */
    private Integer buyers;
    /**
     * 销售额
     */
    private Integer totalSales;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}