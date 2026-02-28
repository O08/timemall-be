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
 * (virtual_product_showcase)实体类
 *
 * @author kancy
 * @since 2025-04-29 11:09:59
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("virtual_product_showcase")
public class VirtualProductShowcase extends Model<VirtualProductShowcase> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 商品
     */
    private String productId;
    /**
     * 资料地址
     */
    private String showcaseUrl;
    /**
     * 顺序
     */
    private Long showcaseOd;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}