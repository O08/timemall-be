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
 * (mentorship)实体类
 *
 * @author kancy
 * @since 2026-03-01 11:38:42
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mentorship")
public class Mentorship extends Model<Mentorship> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * mentorBrandId
     */
    private String mentorBrandId;
    /**
     * menteeBrandId
     */
    private String menteeBrandId;
    /**
     * mentorUserId
     */
    private String mentorUserId;
    /**
     * menteeUserId
     */
    private String menteeUserId;
    /**
     * guidancePeriodEarning
     */
    private BigDecimal guidancePeriodEarning;
    /**
     * guidancePeriodInfluencers
     */
    private Long guidancePeriodInfluencers;
    /**
     * guidancePeriodMessages
     */
    private Long guidancePeriodMessages;
    /**
     * pastYearMessages
     */
    private Long pastYearMessages;
    /**
     * status
     */
    private String status;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}