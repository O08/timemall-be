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
 * (cell_plan_deliver)实体类
 *
 * @author kancy
 * @since 2023-07-17 14:11:08
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cell_plan_deliver")
public class CellPlanDeliver extends Model<CellPlanDeliver> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * cell_plan_order tb id
     */
    private String planOrderId;
    /**
     * 交付物uri
     */
    private String deliver;
    /**
     * 交付物
     */
    private String deliverName;
    /**
     * 预览uri
     */
    private String preview;
    /**
     * 预览
     */
    private String previewName;
    /**
     * mps paper deliver life cycle
     */
    private String tag;
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