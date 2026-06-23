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
 * (mps_chain)实体类
 *
 * @author kancy
 * @since 2023-06-14 16:24:16
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("mps_chain")
public class MpsChain extends Model<MpsChain> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * founder brand id
     */
    private String brandId;
    /**
     * 名称
     */
    private String title;
    /**
     * 正在处理产单数量
     */
    private Integer processingCnt;
    /**
     * 已处理产单数量
     */
    private Integer processedCnt;
    /**
     * mps chain life cycle
     */
    private String tag;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}