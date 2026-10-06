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
 * 用户每日签到记录表(user_checkin_records)实体类
 *
 * @author kancy
 * @since 2026-09-13 15:12:41
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_checkin_records")
public class UserCheckinRecords extends Model<UserCheckinRecords> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brandId
     */
    private String brandId;
    /**
     * 签到日期
     */
    private Date checkinDate;
    /**
     * 签到赠送的源能值
     */
    private Integer rewardElectricity;
    /**
     * 具体签到时间
     */
    private Date createdAt;

}