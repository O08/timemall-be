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
 * (app_desk_element)实体类
 *
 * @author kancy
 * @since 2025-06-11 17:48:57
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_desk_element")
public class AppDeskElement extends Model<AppDeskElement> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 主题区
     */
    private String appDeskTopicId;
    /**
     * 应用名
     */
    private String title;
    /**
     * 简介
     */
    private String preface;
    /**
     * 图标
     */
    private String iconUrl;
    /**
     * 链接
     */
    private String linkUrl;
    /**
     * 浏览量
     */
    private Long views;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}