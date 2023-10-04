package com.norm.timemall.app.base.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (customer)实体类
 *
 * @author kancy
 * @since 2022-10-24 19:38:28
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("customer")
public class Customer extends Model<Customer> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * socialLogin
     */
    private Integer socialLogin;
    /**
     * socialAccounts
     */
    private String socialAccounts;
    /**
     * customerName
     */
    private String customerName;
    /**
     * notifyEmail
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String notifyEmail;
    /**
     * notifyPhone
     */
    private String notifyPhone;
    /**
     * 登录时使用的手机号或者邮箱
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String loginName;
    /**
     * registAt
     */
    private Date registAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;
    /**
     * password
     */
    private String password;

}