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
 * (app_viber_post_interect)实体类
 *
 * @author kancy
 * @since 2025-12-27 10:06:52
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_viber_post_interact")
public class AppViberPostInteract extends Model<AppViberPostInteract> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * postId
     */
    private String postId;
    /**
     * readerBrandId
     */
    private String readerBrandId;
    /**
     * hasLike
     */
    private Integer hasLike;
    /**
     * hasShare
     */
    private Integer hasShare;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}