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
 * (proprietary_trading_pricing)实体类
 *
 * @author kancy
 * @since 2023-02-04 16:06:43
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("proprietary_trading_pricing")
public class ProprietaryTradingPricing extends Model<ProprietaryTradingPricing> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 自营物品id
     */
    private String tradingId;
    /**
     * price
     */
    private BigDecimal price;
    /**
     * 标准付费单位
     */
    private String sbu;
    /**
     * 付费单位说明
     */
    private String sbuDesc;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}