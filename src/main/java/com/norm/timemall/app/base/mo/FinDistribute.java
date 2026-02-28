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
 * (fin_distribute)实体类
 *
 * @author kancy
 * @since 2023-03-15 16:09:57
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("fin_distribute")
public class FinDistribute extends Model<FinDistribute> implements Serializable {
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
     * brandId
     */
    private String brandId;
    /**
     * amount
     */
    private BigDecimal amount;

}