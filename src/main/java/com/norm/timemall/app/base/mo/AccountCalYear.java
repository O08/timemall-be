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
 * (account_cal_year)实体类
 *
 * @author kancy
 * @since 2023-03-02 10:21:25
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_cal_year")
public class AccountCalYear extends Model<AccountCalYear> implements Serializable {
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
     * year
     */
    private String year;
    /**
     * 持有价值流入
     */
    private BigDecimal in;
    /**
     * 持有价值流出
     */
    private BigDecimal out;
    /**
     * 可提取币种收入
     */
    private BigDecimal drawableIn;
    /**
     * 可提取币种支出
     */
    private BigDecimal drawableOut;

}