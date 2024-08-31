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
 * (mini_app_library)实体类
 *
 * @author kancy
 * @since 2024-08-30 13:46:20
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mini_app_library")
public class MiniAppLibrary extends Model<MiniAppLibrary> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 原型名称
     */
    private String appName;
    /**
     * 原型简介
     */
    private String appDesc;
    /**
     * 原型logo
     */
    private String appLogo;
    /**
     * 原型封面
     */
    private String appCover;
    /**
     * 原型状态：0-编辑中，1-已上线
     */
    private String appTag;
    /**
     * 用户侧入口
     */
    private String appViewUrl;
    /**
     * 管理员入口
     */
    private String appAdminUrl;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}