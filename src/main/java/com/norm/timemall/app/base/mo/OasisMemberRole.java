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
 * (oasis_member_role)实体类
 *
 * @author kancy
 * @since 2025-08-23 11:24:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_member_role")
public class OasisMemberRole extends Model<OasisMemberRole> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    /**
     * 部落
     */
    private String oasisId;
    /**
     * 成员
     */
    private String memberBrandId;
    /**
     * 身份组
     */
    private String oasisRoleId;
    /**
     * 身份组起始日
     */
    private Date startsAt;
    /**
     * 身份组结束日
     */
    private Date endsAt;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}