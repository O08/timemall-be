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
 * (blv_redeem_logs)实体类
 *
 * @author kancy
 * @since 2026-08-25 09:19:45
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("blv_redeem_logs")
public class BlvRedeemLogs extends Model<BlvRedeemLogs> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 兑换用户
     */
    private String brandId;
    /**
     * 关联的兑换码主表ID
     */
    private String codeId;
    /**
     * 兑换时间
     */
    private Date claimAt;

}