package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (brand)实体类
 *
 * @author kancy
 * @since 2022-10-26 15:23:17
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("brand")
public class Brand extends Model<Brand> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brandName
     */
    private String brandName;
    /**
     * 头衔或简介
     */
    private String title;
    /**
     * 店铺介绍封面
     */
    private String cover;
    /**
     * 头像
     */
    private String avator;
    /**
     * customerId
     */
    private String customerId;
    /**
     * wechat
     */
    private String wechat;
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
     * 银行卡持卡人
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String cardholder;
    /**
     * 银行卡卡号
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String cardno;
    /**
     * alipay
     */
    private String alipay;
    /**
     * wechatpay
     */
    private String wechatpay;
    /**
     * experience
     */
    private Object experience;
    /**
     * skills
     */
    private Object skills;
    /**
     * location
     */
    private String location;

    /**
     * brand 状态
     */
    private String mark;

    /**
     * brand links
     */
    private Object links;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}