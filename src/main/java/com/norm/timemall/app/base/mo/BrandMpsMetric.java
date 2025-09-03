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
 * (brand_mps_metric)实体类
 *
 * @author kancy
 * @since 2025-09-02 17:55:39
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("brand_mps_metric")
public class BrandMpsMetric extends Model<BrandMpsMetric> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 用户
     */
    private String userBrandId;
    /**
     * 商单基金
     */
    private BigDecimal funds;
    /**
     * 供应商数量
     */
    private Integer suppliers;
    /**
     * 总采购金额
     */
    private BigDecimal totalSpent;
    /**
     * 已发布的商单数量
     */
    private Integer tasksPosted;
    /**
     * 已完成的商单数量
     */
    private Integer tasksFinished;
    /**
     * 招商中的商单数量
     */
    private Integer tasksBiding;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}