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
 * (flier_copy)实体类
 *
 * @author kancy
 * @since 2026-07-30 13:49:10
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("flier_copy")
public class FlierCopy extends Model<FlierCopy> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * flierId
     */
    private String flierId;
    /**
     * receiverBrandId
     */
    private String receiverBrandId;
    /**
     * hasLike
     */
    private String hasLike;
    /**
     * hasClickCta
     */
    private String hasClickCta;
    /**
     * createdAt
     */
    private Date createdAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}