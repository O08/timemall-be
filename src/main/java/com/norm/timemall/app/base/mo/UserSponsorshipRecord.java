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
 * 用户赞助/打赏记录表(user_sponsorship_record)实体类
 *
 * @author kancy
 * @since 2026-10-13 19:49:34
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_sponsorship_record")
public class UserSponsorshipRecord extends Model<UserSponsorshipRecord> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID（如分布式UUID/Snowflake）
     */
    @TableId
	private String id;
    /**
     * 赞助人用户ID
     */
    private String sponsorBrandId;
    /**
     * 受赞助人用户ID
     */
    private String sponsoreeBrandId;
    /**
     * 赞助金额
     */
    private BigDecimal amount;
    /**
     * 币种（如：CNY-人民币, USD-美元）
     */
    private String currency;
    /**
     * 赞助者附言/留言
     */
    private String sponsorMessage;
    /**
     * 记录创建时间
     */
    private Date createdAt;
    /**
     * 记录更新时间
     */
    private Date modifiedAt;

}