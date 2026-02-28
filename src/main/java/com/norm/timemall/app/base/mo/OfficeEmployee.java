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
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (office_employee)实体类
 *
 * @author kancy
 * @since 2025-11-18 11:53:55
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("office_employee")
public class OfficeEmployee extends Model<OfficeEmployee> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    private String oasisId;
    /**
     * uid
     */
    private String uid;
    /**
     * employeeBrandId
     */
    private String employeeBrandId;
    /**
     * employeeNumber
     */
    private String employeeNumber;
    /**
     * employeeName
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String employeeName;

    private String photo;
    /**
     * gender
     */
    private String gender;
    /**
     * birthdate
     */
    private Date birthdate;
    /**
     * education
     */
    private String education;
    /**
     * major
     */
    private String major;
    /**
     * phone
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String phone;
    /**
     * email
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String email;
    /**
     * departmentId
     */
    private String departmentId;
    /**
     * role
     */
    private String role;
    /**
     * level
     */
    private String level;
    /**
     * salary
     */
    private BigDecimal salary;
    /**
     * officeLocation
     */
    private String officeLocation;
    /**
     * hireDate
     */
    private Date hireDate;
    /**
     * netWorth
     */
    private BigDecimal netWorth;
    /**
     * status
     */
    private String status;
    /**
     * genre
     */
    private String genre;
    /**
     * remark
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