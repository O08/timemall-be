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
 * (subscription)实体类
 *
 * @author kancy
 * @since 2025-07-25 14:26:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("subscription")
public class Subscription extends Model<Subscription> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    /**
     * 名称
     */
    private String name;
    /**
     * 描述
     */
    private String description;
    /**
     * 订阅人类型：brandoasis
     */
    private String subscriberType;
    /**
     * 商家
     */
    private String sellerBrandId;
    /**
     * 买家
     */
    private String subscriberFid;
    /**
     * 套餐
     */
    private String planId;
    /**
     * 试用期起始
     */
    private Date trialPeriodStartAt;
    /**
     * 试用期结束
     */
    private Date trialPeriodEndAt;
    /**
     * 订阅起始日
     */
    private Date startsAt;
    /**
     * 订阅结束日
     */
    private Date endsAt;
    /**
     * 取消订阅时间
     */
    private Date canceledAt;
    /**
     * 最新单品价格
     */
    private BigDecimal recentPlanPrice;
    /**
     * 状态
     */
    private String status;
    /**
     * 缴费频率
     */
    private String billCalendar;
    /**
     * 备注
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