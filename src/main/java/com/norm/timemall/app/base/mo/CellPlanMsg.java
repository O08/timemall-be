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
 * (cell_plan_msg)实体类
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cell_plan_msg")
public class CellPlanMsg extends Model<CellPlanMsg> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * msgId
     */
    @TableId
	private String msgId;
    /**
     * authorId
     */
    private String authorId;
    /**
     * planOrderId
     */
    private String planOrderId;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息
     */
    private String msg;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}