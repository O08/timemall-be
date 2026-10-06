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
 * (oasis_fast_link)实体类
 *
 * @author kancy
 * @since 2025-03-07 10:19:23
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_fast_link")
public class OasisFastLink extends Model<OasisFastLink> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * oasis tb id
     */
    private String oasisId;
    /**
     * link 名称
     */
    private String title;
    /**
     * 链接地址
     */
    private String linkUrl;
    /**
     * 链接描述
     */
    private String linkDetail;
    /**
     * 链接图标
     */
    private String logo;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}