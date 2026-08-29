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
 * (commission_deliver)实体类
 *
 * @author kancy
 * @since 2023-08-18 11:34:31
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("commission_deliver")
public class CommissionDeliver extends Model<CommissionDeliver> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * commission tb id
     */
    private String commissionId;
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