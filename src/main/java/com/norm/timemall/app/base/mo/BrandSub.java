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
 * @since 2024-05-02 09:29:52
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
     * 行业
     */
    private String industryCode;
    /**
     * 支持自由合作： 0-不支持，1-支持
     */
    private String supportFreeCooperation;
    /**
     * 合作资源
     */
    private String cooperationScope;
    /**
     * 空闲，可以接受工作机会：0-不接受，1-接受
     */
    private String availableForWork;
    /**
     * 聘用，招聘中：0-无招聘，1-有招聘
     */
    private String hiring;
    /**
     * 法定的企业结构，账号类型为组织时候生效
     */
    private String typeOfBusiness;
    /**
     * 行业 text
     */
    private String industry;

}