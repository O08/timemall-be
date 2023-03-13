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
 * (account_cal_month)实体类
 *
 * @author kancy
 * @since 2023-03-02 10:21:25
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("account_cal_month")
public class AccountCalMonth extends Model<AccountCalMonth> implements Serializable {
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
     * year
     */
    private String year;
    /**
     * month
     */
    private String month;
    /**
     * in
     */
    private BigDecimal in;
    /**
     * out
     */
    private BigDecimal out;

}