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
 * (virtual_product)实体类
 *
 * @author kancy
 * @since 2025-04-29 11:09:58
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("virtual_product")
public class VirtualProduct extends Model<VirtualProduct> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品单价
     */
    private BigDecimal productPrice;
    /**
     * 产品文案
     */
    private String productDesc;
    /**
     * 主图
     */
    private String thumbnailUrl;
    /**
     * 库存
     */
    private Integer inventory;
    /**
     * 提供发票标识
     */
    private String provideInvoice;
    /**
     * 交付说明
     */
    private String deliverNote;
    /**
     * 交付附件
     */
    private String deliverAttachment;
    /**
     * 状态
     */
    private String productStatus;
    /**
     * 商家
     */
    private String sellerBrandId;
    /**
     * 销量
     */
    private Integer salesVolume;
    /**
     * 点击
     */
    private Integer clicks;
    /**
     * 曝光
     */
    private Integer views;
    /**
     * 标签
     */
    private Object tags;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}