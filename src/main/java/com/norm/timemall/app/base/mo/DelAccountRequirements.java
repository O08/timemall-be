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
 * (del_account_requirements)实体类
 *
 * @author kancy
 * @since 2023-10-05 15:07:33
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("del_account_requirements")
public class DelAccountRequirements extends Model<DelAccountRequirements> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * userId
     */
    private String userId;
    /**
     * itemCode
     */
    private String itemCode;
    /**
     * item
     */
    private String item;
    /**
     * targetVal
     */
    private BigDecimal targetVal;
    /**
     * currentVal
     */
    private BigDecimal currentVal;
    /**
     * landUrl
     */
    private String landUrl;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}