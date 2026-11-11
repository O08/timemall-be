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
 * (dsp_case)实体类
 *
 * @author kancy
 * @since 2025-04-14 15:02:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("dsp_case")
public class DspCase extends Model<DspCase> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 案件编号
     */
    private String caseNo;
    /**
     * 类型
     */
    private String fraudType;
    /**
     * 场景
     */
    private String scene;
    /**
     * 被举报物料
     */
    private String sceneUrl;
    /**
     * 描述
     */
    private String caseDesc;
    /**
     * 涉案金额
     */
    private BigDecimal caseAmount;
    /**
     * 理赔金额
     */
    private BigDecimal claimAmount;
    /**
     * 调解员
     */
    private String peacemakerBrandId;
    /**
     * 吹哨人
     */
    private String informerBrandId;
    /**
     * 被举报人
     */
    private String defendantBrandId;
    /**
     * 优先级
     */
    private String casePriority;
    /**
     * 状态
     */
    private String caseStatus;
    /**
     * 处理方案
     */
    private String solution;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}