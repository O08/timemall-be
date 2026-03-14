package com.norm.timemall.app.base.mo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (office_payroll)实体类
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("office_payroll")
public class OfficePayroll extends Model<OfficePayroll> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id",type = IdType.INPUT) // 主键手动输入
	private String id;
    /**
     * oasisId
     */
    private String oasisId;
    /**
     * employeeId
     */
    private String employeeId;
    /**
     * employeeCompany
     */
    private String employeeCompany;

    private String employeeRole;
    /**
     * employeeDepartment
     */
    private String employeeDepartment;
    /**
     * employeeStatus
     */
    private String employeeStatus;
    /**
     * employeeGenre
     */
    private String employeeGenre;
    /**
     * title
     */
    private String title;
    /**
     * grossPay
     */
    private BigDecimal grossPay;
    /**
     * deductions
     */
    private BigDecimal deductions;
    /**
     * withholdAndRemitTax
     */
    private BigDecimal withholdAndRemitTax;
    /**
     * netPay
     */
    private BigDecimal netPay;
    /**
     * benefits
     */
    private Object benefits;
    /**
     * compensations
     */
    private Object compensations;

    private String paymentId;
    /**
     * paymentDate
     */
    private Date paymentDate;
    /**
     * status
     */
    private String status;

    private String category;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}