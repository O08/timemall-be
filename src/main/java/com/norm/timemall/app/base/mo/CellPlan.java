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
 * (cell_plan)实体类
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cell_plan")
public class CellPlan extends Model<CellPlan> implements Serializable {
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
     * 单品类型：小鸟，老鹰，信天翁
     */
    private String planType;
    /**
     * 单品类型
     */
    private String planTypeDesc;
    /**
     * 单品 名称
     */
    private String title;
    /**
     * 单品 内容
     */
    private String content;
    /**
     * 单品 功能
     */
    private String feature;
    /**
     * 售价
     */
    private BigDecimal price;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}