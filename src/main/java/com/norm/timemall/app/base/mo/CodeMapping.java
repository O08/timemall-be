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
 * (code_mapping)实体类
 *
 * @author kancy
 * @since 2024-04-19 15:48:08
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("code_mapping")
public class CodeMapping extends Model<CodeMapping> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * codeType
     */
    private String codeType;
    /**
     * codeTypeDesc
     */
    private String codeTypeDesc;
    /**
     * itemCode
     */
    private String itemCode;
    /**
     * item
     */
    private String item;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}