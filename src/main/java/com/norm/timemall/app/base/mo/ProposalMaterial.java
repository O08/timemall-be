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
 * (proposal_material)实体类
 *
 * @author kancy
 * @since 2025-07-05 17:11:01
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("proposal_material")
public class ProposalMaterial extends Model<ProposalMaterial> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 提案
     */
    private String proposalId;
    /**
     * 类型：seller -- 商家上传的资料；buyer -- 买家上传的资料；deliver -- 交付资料
     */
    private String materialType;
    /**
     * 资料
     */
    private String materialName;
    /**
     * 资料地址
     */
    private String materialUrl;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}