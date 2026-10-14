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
 * 风险审计表(risk_audit)实体类
 *
 * @author kancy
 * @since 2024-06-10 10:58:24
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("risk_audit")
public class RiskAudit extends Model<RiskAudit> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private Integer id;
    /**
     * 风险类型
     */
    private String riskType;
    /**
     * 风险代码
     */
    private String riskCode;
    /**
     * 风险名称
     */
    private String riskName;
    /**
     * 风险值
     */
    private String riskVal;
    /**
     * 波动
     */
    private String difference;
    /**
     * 是否需要核查
     */
    private String needCheck;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}