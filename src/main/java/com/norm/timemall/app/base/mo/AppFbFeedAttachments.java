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
 * (app_fb_feed_attachments)实体类
 *
 * @author kancy
 * @since 2025-11-11 09:53:27
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_fb_feed_attachments")
public class AppFbFeedAttachments extends Model<AppFbFeedAttachments> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 帖子
     */
    private String feedId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件资源
     */
    private String fileUri;
    /**
     * 文件类型
     */
    private String fileType;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}