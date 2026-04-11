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
 * (transactions)实体类
 *
 * @author kancy
 * @since 2023-03-02 10:23:07
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("transactions")
public class Transactions extends Model<Transactions> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 交易对象
     */
    private String fid;

    /**
     * 交易对象type: oasis or brand or user or other
     */
    private String fidType;
    /**
     * 交易号
     */
    private String transNo;
    /**
     * transType
     */
    private String transType;
    /**
     * transTypeDesc
     */
    private String transTypeDesc;
    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * debit or credit
     */
    private Integer direction;
    /**
     * 备注
     */
    private String remark;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}