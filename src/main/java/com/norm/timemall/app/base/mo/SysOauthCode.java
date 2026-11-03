package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (sys_oauth_code)实体类
 *
 * @author kancy
 * @since 2026-04-19 11:56:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_oauth_code")
public class SysOauthCode extends Model<SysOauthCode> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private Long id;
    /**
     * code
     */
    private String code;
    /**
     * userId
     */
    private String userId;
    /**
     * expireTime
     */
    private LocalDateTime expireTime;
    /**
     * createTime
     */
    private LocalDateTime createTime;

}