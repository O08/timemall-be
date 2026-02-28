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
 * (oasis_channel)实体类
 *
 * @author kancy
 * @since 2024-08-30 13:46:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis_channel")
public class OasisChannel extends Model<OasisChannel> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 基地Id
     */
    private String oasisId;
    /**
     * 原型Id
     */
    private String appId;
    /**
     * 频道名称
     */
    private String channelName;
    /**
     * 频道简介
     */
    private String channelDesc;
    /**
     * 状态：0-编辑中，1-已上线，2-下线
     */
    private String channelTag;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}