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
 * (app_fb_feed)实体类
 *
 * @author kancy
 * @since 2025-01-23 13:28:27
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_fb_feed")
public class AppFbFeed extends Model<AppFbFeed> implements Serializable {
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
     * 导语
     */
    private String preface;
    /**
     * 正文
     */
    private String richMediaContent;
    /**
     * cta 链接
     */
    private String ctaPrimaryLabel;
    /**
     * cta 链接
     */
    private String ctaPrimaryUrl;
    /**
     * cta 标签
     */
    private String ctaSecondaryLabel;
    /**
     * cta链接
     */
    private String ctaSecondaryUrl;
    /**
     * 帖子封面
     */
    private String coverUrl;
    /**
     * 加精
     */
    private String highlight;
    /**
     * 帖子状态： 0 - 关闭评论 1 - 开启评论
     */
    private String commentTag;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}