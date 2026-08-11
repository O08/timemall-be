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
 * (office_dashboard)实体类
 *
 * @author kancy
 * @since 2025-11-20 09:55:38
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("office_dashboard")
public class OfficeDashboard extends Model<OfficeDashboard> implements Serializable {
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
     * employees
     */
    private Integer employees;
    /**
     * pendingPayments
     */
    private BigDecimal pendingPayments;
    /**
     * monthlyExpense
     */
    private BigDecimal monthlyExpense;
    /**
     * monthlyPayrolls
     */
    private Integer monthlyPayrolls;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}