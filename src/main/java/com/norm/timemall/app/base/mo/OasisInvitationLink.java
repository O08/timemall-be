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
 * (oasis_invitation_link)实体类
 *
 * @author kancy
 * @since 2026-01-29 10:21:00
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_invitation_link")
public class OasisInvitationLink extends Model<OasisInvitationLink> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * inviterBrandId
     */
    private String inviterBrandId;
    /**
     * oasisId
     */
    private String oasisId;
    /**
     * invitationCode
     */
    private String invitationCode;
    /**
     * grantedOasisRoleId
     */
    private String grantedOasisRoleId;
    /**
     * grantedOasisRoleName
     */
    private String grantedOasisRoleName;
    /**
     * expireTime
     */
    private Date expireTime;
    /**
     * 0 usually represents unlimited if handled by logic
     */
    private Integer maxUses;
    /**
     * usageCount
     */
    private Integer usageCount;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}