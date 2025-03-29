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
 * (order_details)实体类
 *
 * @author kancy
 * @since 2022-10-26 15:48:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("order_details")
public class OrderDetails extends Model<OrderDetails> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 客户id
     */
    private String consumerId;
    /**
     * 客户姓名
     */
    private String consumerName;
    /**
     * cellId
     */
    private String cellId;
    /**
     * cellTitle
     */
    private String cellTitle;
    /**
     * brandId
     */
    private String brandId;
    /**
     * brandName
     */
    private String brandName;
    /**
     * total
     */
    private BigDecimal total;
    /**
     * quantity
     */
    private Integer quantity;
    /**
     * 付费单位 year quarter month day hour minute second
     */
    private String sbu;
    private String orderType;
    /**
     * cancelled
     */
    private Integer cancelled;
    /**
     * cancelledReason
     */
    private String cancelledReason;
    /**
     * cancelledAt
     */
    private Date cancelledAt;
    /**
     * refunded
     */
    private Integer refunded;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}