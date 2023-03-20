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
 * (market_object_record)实体类
 *
 * @author kancy
 * @since 2023-02-27 10:39:37
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_object_record")
public class MarketObjectRecord extends Model<MarketObjectRecord> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    /**
     * 互换编号：标记同一次互换
     */
    private String swapNo;
    /**
     * 标的id
     */
    private String objId;
    /**
     *  债务人 BrandId
     */
    private String debitId;
    /**
     * 债权人  BrandId
     */
    private String creditId;
    /**
     * 标签
     */
    private String tag;
    /**
     * 合作意向/拥有等标记
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

}