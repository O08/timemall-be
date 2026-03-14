package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (cell_indices)实体类
 *
 * @author kancy
 * @since 2022-12-02 14:09:48
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("cell_indices")
public class CellIndices extends Model<CellIndices> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * cellId
     */
    private String cellId;
    /**
     * 曝光数
     */
    private Integer impressions;
    /**
     * 点击次数
     */
    private Integer clicks;
    /**
     * 预定次数
     */
    private Integer orders;

    /**
     * 单品小鸟销量
     */
    private Integer birdPurchases;
    /**
     * 单品老鹰销量
     */
    private Integer eaglePurchases;
    /**
     * 单品信天翁销量
     */
    private Integer albatrossPurchases;

}