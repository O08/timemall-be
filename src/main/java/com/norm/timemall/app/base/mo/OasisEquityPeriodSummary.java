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
 * (oasis_equity_period_summary)实体类
 *
 * @author kancy
 * @since 2026-04-07 16:41:50
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_equity_period_summary")
public class OasisEquityPeriodSummary extends Model<OasisEquityPeriodSummary> implements Serializable {
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
     * credit
     */
    private BigDecimal credit;
    /**
     * shares
     */
    private Integer shares;
    /**
     * sold
     */
    private Integer sold;
    /**
     * writeOff
     */
    private Integer writeOff;
    /**
     * redemption
     */
    private Integer redemption;
    /**
     * sponsorshipFee
     */
    private BigDecimal sponsorshipFee;
    /**
     * writeOffFee
     */
    private BigDecimal writeOffFee;
    /**
     * redemptionFee
     */
    private BigDecimal redemptionFee;

    /**
     * totalSponsor
     */
    private Integer totalSponsor;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}