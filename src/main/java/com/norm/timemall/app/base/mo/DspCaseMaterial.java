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
 * (dsp_case_material)实体类
 *
 * @author kancy
 * @since 2025-04-14 15:02:32
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("dsp_case_material")
public class DspCaseMaterial extends Model<DspCaseMaterial> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 案件编号
     */
    private String caseNo;
    /**
     * 类型：peacemaker -- 调解员上传的资料；informer -- 吹哨人上传的资料；defendant -- 被举报人上传资料
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