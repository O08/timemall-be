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
 * (mps_template)实体类
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mps_template")
public class MpsTemplate extends Model<MpsTemplate> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * mps chain id
     */
    private String chainId;
    /**
     * 名称
     */
    private String title;
    /**
     * 需求
     */
    private String sow;
    /**
     * 职能
     */
    private String piece;
    /**
     * 报酬
     */
    private BigDecimal bonus;
    /**
     * 一级供应商
     */
    private String firstSupplier;
    /**
     * 机遇期
     */
    private Integer duration;
    /**
     * 交付周期
     */
    private Integer deliveryCycle;
    /**
     * 合约有效期
     */
    private Integer contractValidityPeriod;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}