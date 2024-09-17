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
 * (account)实体类
 *
 * @author kancy
 * @since 2023-03-15 11:04:49
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("fin_account")
public class FinAccount extends Model<FinAccount> implements Serializable {
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
     * 持有价值
     */
    private BigDecimal amount;
    /**
     * 可提取余额
     */
    private BigDecimal drawable;

}