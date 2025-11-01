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
 * (oasis_membership_tier)实体类
 *
 * @author kancy
 * @since 2025-10-29 15:52:41
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_membership_tier")
public class OasisMembershipTier extends Model<OasisMembershipTier> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 部落
     */
    private String oasisId;
    /**
     * 套餐名称
     */
    private String tierName;
    /**
     * 套餐说明
     */
    private String tierDescription;
    /**
     * 配图
     */
    private String thumbnail;
    /**
     * 权益身份组
     */
    private String subscribeRole;
    /**
     * 单价
     */
    private BigDecimal price;
    /**
     * 销量
     */
    private Integer soldOrders;
    /**
     * 订阅中的会员数
     */
    private Integer members;
    /**
     * 状态
     */
    private String status;
    /**
     * 顺序
     */
    private long od;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}