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
 * (subs_plan)实体类
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("subs_plan")
public class SubsPlan extends Model<SubsPlan> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 类型
     */
    private String planType;
    /**
     * 商品
     */
    private String productId;

    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 功能
     */
    private Object features;
    /**
     * 试用期
     */
    private Integer trialPeriod;
    /**
     * 宽限期
     */
    private Integer gracePeriod;
    /**
     * 订阅量
     */
    private Long sales;
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