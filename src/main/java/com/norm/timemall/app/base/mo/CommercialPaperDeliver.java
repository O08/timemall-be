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
 * (commercial_paper_deliver)实体类
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("commercial_paper_deliver")
public class CommercialPaperDeliver extends Model<CommercialPaperDeliver> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * paper id
     */
    private String paperId;
    /**
     * 交付物
     */
    private String deliver;
    /**
     * 交付物
     */
    private String deliverName;
    /**
     * 预览内容
     */
    private String preview;
    /**
     * 预览内容
     */
    private String previewName;
    /**
     * mps paper life cycle
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