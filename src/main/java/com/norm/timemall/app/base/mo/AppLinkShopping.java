package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (app_link_shopping)实体类
 *
 * @author kancy
 * @since 2025-04-02 14:46:02
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_link_shopping")
public class AppLinkShopping extends Model<AppLinkShopping> implements Serializable {
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
     * 商品名称
     */
    private String title;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 商品链接
     */
    private String linkUrl;
    /**
     * 商品封面
     */
    private String coverUrl;
    /**
     * 点击量
     */
    private Integer views;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}