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
 * (app_viber_post)实体类
 *
 * @author kancy
 * @since 2025-12-27 10:06:52
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_viber_post")
public class AppViberPost extends Model<AppViberPost> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    private String oasisId;
    /**
     * channel
     */
    private String channel;
    /**
     * authorBrandId
     */
    private String authorBrandId;
    /**
     * textMsg
     */
    private String textMsg;
    /**
     * embed
     */
    private Object embed;
    /**
     * shares
     */
    private Integer shares;
    /**
     * comments
     */
    private Integer comments;
    /**
     * likes
     */
    private Integer likes;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}