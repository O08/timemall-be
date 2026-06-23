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
 * (proposal)实体类
 *
 * @author kancy
 * @since 2025-07-05 17:11:01
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("proposal")
public class Proposal extends Model<Proposal> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 商家
     */
    private String sellerBrandId;
    /**
     * 买家
     */
    private String buyerBrandId;
    /**
     * 编号
     */
    private String projectNo;
    /**
     * 项目
     */
    private String projectName;

    /**
     * 项目status
     */
    private String projectStatus;
    /**
     * 开始时间
     */
    private Date projectStarts;
    /**
     * 结束时间
     */
    private Date projectEnds;
    /**
     * 服务与费用
     */
    private Object services;
    /**
     * 服务履约进度
     */
    private String serviceProgress;
    /**
     * 条款或其他
     */
    private String extraContent;
    /**
     * 提案总金额
     */
    private BigDecimal total;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}