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
 * (oasis_equity_order)实体类
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_equity_order")
public class OasisEquityOrder extends Model<OasisEquityOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * equityPeriodId
     */
    private String equityPeriodId;
    /**
     * sponsorBrandId
     */
    private String sponsorBrandId;
    /**
     * donationAmount
     */
    private BigDecimal donationAmount;
    /**
     * redemptionAmount
     */
    private BigDecimal redemptionAmount;
    /**
     * shares
     */
    private Integer shares;
    /**
     * 1-holding, 2-redeemed, 3-write_off
     */
    private String status;
    /**
     * settlementDate
     */
    private Date settlementDate;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}