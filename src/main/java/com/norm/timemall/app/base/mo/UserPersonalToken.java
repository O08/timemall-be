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
 * (user_personal_token)实体类
 *
 * @author kancy
 * @since 2026-08-13 14:17:27
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_personal_token")
public class UserPersonalToken extends Model<UserPersonalToken> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * Owner user ID
     */
    private String userId;

    private String brandId;
    /**
     * Token identifier name (e.g., CI-CD)
     */
    private String name;
    /**
     * Last 4 digits of token for UI display
     */
    private String tokenLast;
    /**
     * SHA-256 hash of the full token string
     */
    private String tokenHash;
    /**
     * Null means never expires
     */
    private LocalDateTime expiresAt;
    /**
     * lastUsedAt
     */
    private LocalDateTime lastUsedAt;
    /**
     * createdAt
     */
    private LocalDateTime createdAt;

}