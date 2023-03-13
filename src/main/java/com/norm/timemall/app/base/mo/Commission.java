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
 * (commission)实体类
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("commission")
public class Commission extends Model<Commission> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * oasisId
     */
    private String oasisId;
    /**
     * 标题
     */
    private String title;
    /**
     * 标签
     */
    private String tag;
    /**
     * 创建人id
     */
    private String founder;
    /**
     * 执行任务id
     */
    private String worker;
    /**
     * 奖金
     */
    private BigDecimal bonus;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}