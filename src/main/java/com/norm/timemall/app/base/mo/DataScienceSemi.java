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
 * (data_science_semi)实体类
 *
 * @author kancy
 * @since 2023-11-17 10:05:43
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("data_science_semi")
public class DataScienceSemi extends Model<DataScienceSemi> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 数据摘要
     */
    private String snippet;
    /**
     * 数据明细
     */
    private String details;
    /**
     * 数据源
     */
    private String fromWhere;
    /**
     * createAt
     */
    private Date createAt;

}