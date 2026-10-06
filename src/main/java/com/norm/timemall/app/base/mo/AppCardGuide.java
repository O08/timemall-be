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
 * (app_card_guide)实体类
 *
 * @author kancy
 * @since 2025-03-14 14:33:06
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_card_guide")
public class AppCardGuide extends Model<AppCardGuide> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * oasis channel id
     */
    private String oasisChannelId;
    /**
     * 布局：music,art,book ..etc
     */
    private String layout;
    /**
     * 0: all member  post , 1 : only admin  post
     */
    private String postStrategy;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}