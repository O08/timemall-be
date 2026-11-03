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
 * (customer_interests)实体类
 *
 * @author kancy
 * @since 2023-10-05 15:07:33
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("customer_interests")
public class CustomerInterests extends Model<CustomerInterests> implements Serializable {
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
     * landUrl
     */
    private String landUrl;
    /**
     * val
     */
    private BigDecimal val;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}