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
 * (app_embed_web)实体类
 *
 * @author kancy
 * @since 2026-08-20 19:30:58
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_embed_web")
public class AppEmbedWeb extends Model<AppEmbedWeb> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * oasisChannelId
     */
    private String oasisChannelId;
    /**
     * webUri
     */
    private String webUri;
    /**
     * createdAt
     */
    private Date createdAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}