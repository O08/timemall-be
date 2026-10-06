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
 * (blv_redeem_codes)实体类
 *
 * @author kancy
 * @since 2026-08-25 09:19:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("blv_redeem_codes")
public class BlvRedeemCodes extends Model<BlvRedeemCodes> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 兑换码字符串
     */
    private String giftCode;
    /**
     * 兑换码类型
     */
    private String rewardType;
    /**
     * 赠送的会员天数（0表示不赠送）
     */
    private Integer vipDays;
    /**
     * 赠送的源能数量（0表示不赠送）
     */
    private Integer pointAmount;
    /**
     * 最大可兑换总次数
     */
    private Integer maxUses;
    /**
     * 已被兑换的次数
     */
    private Integer usedCount;
    /**
     * 状态：1-有效，0-禁用
     */
    private String status;
    /**
     * 有效期开始
     */
    private Date startTime;
    /**
     * 有效期结束
     */
    private Date endTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 达人祝福
     */
    private String kolMessage;
    /**
     * createdAt
     */
    private Date createdAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}