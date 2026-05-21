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
 * (supplier)实体类
 *
 * @author kancy
 * @since 2026-05-20 17:50:27
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("supplier")
public class Supplier extends Model<Supplier> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
	private String id;
    /**
     * 供应商品牌ID
     */
    private String supplierBrandId;
    /**
     * 采购商品牌ID
     */
    private String purchaserBrandId;
    /**
     * 业务信息
     */
    private String biz;
    /**
     * NDA文件地址
     */
    private String ndaFileUri;
    /**
     * 供应商类型，STRATEGIC-战略型、LEVERAGE-杠杆型、BOTTLENECK-瓶颈型、ROUTINE-常规型
     */
    private String supplierLevel;
    /**
     * 供应商状态，ACTIVE-正常、BLACKLISTED-黑名单、TERMINATED-清退、FROZEN-冻结
     */
    private String supplierStatus;
    /**
     * 体验评分
     */
    private Integer ux;
    /**
     * 合规评分
     */
    private Integer compliance;
    /**
     * 是否提供发票 1-是、0-否
     */
    private String canProvideInvoice;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}