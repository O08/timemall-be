package com.norm.timemall.app.base.mo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (private_msg)实体类
 *
 * @author kancy
 * @since 2023-08-18 16:49:11
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("private_msg")
public class PrivateMsg extends Model<PrivateMsg> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * msgId
     */
    @TableId(type = IdType.INPUT)
	private String msgId;
    /**
     * 消息编号，标记消息为同一条
     */
    private String msgNo;
    /**
     * fromId
     */
    private String fromId;
    /**
     * toId
     */
    private String toId;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 消息
     */
    private String msg;
    /**
     * ownerUserId
     */
    private String ownerUserId;
    /**
     * 状态
     */
    private String status;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}