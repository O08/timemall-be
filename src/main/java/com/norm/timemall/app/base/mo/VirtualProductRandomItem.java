package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (virtual_product_random_item)实体类
 *
 * @author kancy
 * @since 2025-09-15 14:49:07
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("virtual_product_random_item")
public class VirtualProductRandomItem extends Model<VirtualProductRandomItem> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * virtual product id
     */
    private String productId;
    /**
     * 货品
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String pack;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}