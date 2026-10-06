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
 * (ppc_link)实体类
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ppc_link")
public class PpcLink extends Model<PpcLink> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brand id
     */
    private String supplierBrandId;
    /**
     * 链接名称
     */
    private String linkName;
    /**
     * 监测码
     */
    private String trackCode;
    /**
     * 浏览量
     */
    private Long views;
    /**
     * 有效IP数
     */
    private Long ips;
    /**
     * 收入
     */
    private BigDecimal earning;
    /**
     * 链接地址
     */
    private String linkAddress;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}