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
 * (cell)实体类
 *
 * @author kancy
 * @since 2022-10-25 20:09:25
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cell")
public class Cell extends Model<Cell> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 服务名
     */
    private String title;
    /**
     * cover
     */
    private String cover;
    /**
     * 服务详情页封面
     */
    private String introCover;
    /**
     * content
     */
    private Object content;
    /**
     * brandId
     */
    private String brandId;
    /**
     * mark
     */
    private String mark;
    /**
     * mark
     */
    private int provideInvoice;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}