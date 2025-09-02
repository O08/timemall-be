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
 * (electricity_history)实体类
 *
 * @author kancy
 * @since 2025-09-01 16:38:29
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("electricity_history")
public class ElectricityHistory extends Model<ElectricityHistory> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    private String buyerBrandId;

    private String busiType;
    /**
     * 项目
     */
    private String item;
    /**
     * 1 流入，-1 流出
     */
    private int direction;
    /**
     * 数额
     */
    private Integer amount;
    /**
     * 外部编号
     */
    private String outNo;
    /**
     * 外部编号使用线索
     */
    private String clue;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}