package com.norm.timemall.app.base.mo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (commercial_paper)实体类
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("commercial_paper")
public class CommercialPaper extends Model<CommercialPaper> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
	private String id;
    /**
     * mps_template id
     */
    private String templateId;
    /**
     * mps_template id
     */
    private String mpsId;
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
     * 供应商
     */
    private String supplier;
    /**
     * 采购商
     */
    private String purchaser;
    /**
     * mps paper life cycle
     */
    private String tag;
    /**
     * 机遇期
     */
    private Integer duration;
    /**
     * 交付周期
     */
    private Integer deliveryCycle;
    /**
     * 招商期
     */
    private Integer contractValidityPeriod;

    private Object skills;

    private String difficulty;

    private Integer experience;

    private String location;

    private Integer bidElectricity;

    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}