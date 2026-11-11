package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (app_viber_comment_interect)实体类
 *
 * @author kancy
 * @since 2025-12-27 10:06:52
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_viber_comment_interact")
public class AppViberCommentInteract extends Model<AppViberCommentInteract> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * commentId
     */
    private String commentId;

    private String postId;
    /**
     * readerBrandId
     */
    private String readerBrandId;
    /**
     * 0-no action, 1 -like ,2 - diss
     */
    private Integer likeAction;

}