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
 * (withdraw_record)实体类
 *
 * @author kancy
 * @since 2023-03-16 11:36:48
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("withdraw_record")
public class WithdrawRecord extends Model<WithdrawRecord> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brandId
     */
    private String brandId;
    /**
     * payeeType
     */
    private String payeeType;
    /**
     * payeeAccount
     */
    private String payeeAccount;
    /**
     * payeeRealName
     */
    private String payeeRealName;
    /**
     * amount
     */
    private BigDecimal amount;
    /**
     * feeRate
     */
    private BigDecimal feeRate;
    /**
     * tag
     */
    private String tag;
    /**
     * tagDesc
     */
    private String tagDesc;
    private String msg;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}