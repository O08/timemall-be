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
 * 实物商品信息表(physical_product)实体类
 *
 * @author kancy
 * @since 2026-10-26 15:37:54
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("physical_product")
public class PhysicalProduct extends Model<PhysicalProduct> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 卖家
     */
    private String sellerBrandId;
    /**
     * 商品主图URL
     */
    private String thumbnail;
    /**
     * 商品编码/条形码/SKU号
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品详细描述/富文本
     */
    private String productDesc;
    /**
     * 商品售价
     */
    private BigDecimal price;
    /**
     * 当前可用库存数量
     */
    private Integer inventory;
    /**
     * 历史已售累计销量
     */
    private Integer soldOrders;
    /**
     * 温馨提示/购买须知 (如:拆封不退)
     */
    private String warmReminder;
    /**
     * 商品状态
     */
    private String status;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 最后修改时间
     */
    private Date modifiedAt;

}