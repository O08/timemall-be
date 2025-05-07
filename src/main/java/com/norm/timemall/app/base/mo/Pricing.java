package com.norm.timemall.app.base.mo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (pricing)实体类
 *
 * @author kancy
 * @since 2022-10-26 14:15:39
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("pricing")
public class Pricing extends Model<Pricing> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.INPUT)
	private String id;
    /**
     * cellId
     */
    private String cellId;
    /**
     * price
     */
    private BigDecimal price;
    /**
     * 付费单位 year quarter month day hour minute second
     */
    private String sbu;

}