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
 * (app_desk_topic)实体类
 *
 * @author kancy
 * @since 2025-06-11 17:48:57
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_desk_topic")
public class AppDeskTopic extends Model<AppDeskTopic> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 频道
     */
    private String oasisChannelId;
    /**
     * 主题
     */
    private String title;
    /**
     * 简介
     */
    private String preface;
    /**
     * 顺序
     */
    private Long od;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}