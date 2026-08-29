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
 * (rc_library)实体类
 *
 * @author kancy
 * @since 2024-10-29 15:12:30
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("rc_library")
public class RcLibrary extends Model<RcLibrary> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 资源类型
     */
    private String rcType;
    /**
     * 资源名称
     */
    private String title;
    /**
     * biao qian
     */
    private Object tags;
    /**
     * 封面
     */
    private String thumbnail;
    /**
     * 预览
     */
    private String previewUri;
    /**
     * 下载地址
     */
    private String downloadUri;
    /**
     * 文件地址
     */
    private String fileUri;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}