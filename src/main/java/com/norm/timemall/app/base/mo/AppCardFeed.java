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
 * (app_card_feed)实体类
 *
 * @author kancy
 * @since 2025-03-14 14:33:06
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_card_feed")
public class AppCardFeed extends Model<AppCardFeed> implements Serializable {
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
     * 作者
     */
    private String authorBrandId;
    /**
     * 标题
     */
    private String title;
    /**
     * 描述
     */
    private String subtitle;
    /**
     * 链接
     */
    private String linkUrl;
    /**
     * 帖子封面
     */
    private String coverUrl;
    /**
     * 浏览量
     */
    private Integer views;
    /**
     * 访问状态： 0- 链接无法访问， 1 - 可以访问
     */
    private String available;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}