package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (market_object)实体类
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_object")
public class MarketObject extends Model<MarketObject> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * cellId
     */
    private String cellId;
    /**
     * sbu
     */
    private String sbu;
    /**
     * quantity
     */
    private Integer quantity;
    /**
     * 现价
     */
    private BigDecimal salePrice;
    /**
     * 账面价值
     */
    private BigDecimal objectVal;

}