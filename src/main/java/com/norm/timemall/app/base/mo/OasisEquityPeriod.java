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
 * (oasis_equity_period)实体类
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_equity_period")
public class OasisEquityPeriod extends Model<OasisEquityPeriod> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * oasisId
     */
    private String oasisId;
    /**
     * period
     */
    private Integer period;
    /**
     * shares
     */
    private Integer shares;
    /**
     * price
     */
    private BigDecimal price;
    /**
     * earningYield
     */
    private BigDecimal earningYield;
    /**
     * 1-ACTIVE,0-CLOSED
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