package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (millstone)实体类
 *
 * @author kancy
 * @since 2022-10-26 19:43:08
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("millstone")
public class Millstone extends Model<Millstone> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * orderId
     */
    private String orderId;
    /**
     * Workflow 集合
     */
    private Object stageList;
    /**
     * 工作流状态标识
     */
    private String mark;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

    private String doneStageNo;

}