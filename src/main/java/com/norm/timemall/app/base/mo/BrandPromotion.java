package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (brand_promotion)实体类
 *
 * @author kancy
 * @since 2024-08-03 10:31:53
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("brand_promotion")
public class BrandPromotion extends Model<BrandPromotion> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 品牌id
     */
    private String brandId;
    /**
     * 体验金额度
     */
    private Integer creditPoint;
    /**
     * 体验金促销活动状态
     */
    private String creditPointTag;
    /**
     * 体验金活动发放人次
     */
    private Integer creditPointCnt;
    /**
     * 尝鲜折扣
     */
    private Integer earlyBirdDiscount;
    /**
     * 尝鲜促销活动状态
     */
    private String earlyBirdDiscountTag;
    /**
     * 尝鲜折扣发放人次
     */
    private Integer earlyBirdDiscountCnt;
    /**
     * 复购折扣
     */
    private Integer repurchaseDiscount;
    /**
     * 复购促销活动状态
     */
    private String repurchaseDiscountTag;
    /**
     * 复购折扣发放人次
     */
    private Integer repurchaseDiscountCnt;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}