package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * brand 副表(brand_sub)实体类
 *
 * @author kancy
 * @since 2024-04-18 11:12:12
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("brand_sub")
public class BrandSub extends Model<BrandSub> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brand.id
     */
    private String brandId;
    /**
     * 职业编码：0:自定义，。。。
     */
    private String occupationCode;
    /**
     * 自定义职业
     */
    private String selfDefinedOccupation;
    /**
     * 用户类型： 0-个人；1-机构
     */
    private String brandTypeCode;
    /**
     * 活动：1.静默、2.招聘中、3.寻找联盟、4.蹲工作、5.等自由合伙人、6.疯狂搬砖
     */
    private String activityCode;

}