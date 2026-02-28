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
 * (oasis_role)实体类
 *
 * @author kancy
 * @since 2025-08-23 11:24:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_role")
public class OasisRole extends Model<OasisRole> implements Serializable {
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
     * 身份组编码
     */
    private String roleCode;
    /**
     * 身份组
     */
    private String roleName;
    /**
     * 身份组摘要
     */
    private String roleDesc;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}