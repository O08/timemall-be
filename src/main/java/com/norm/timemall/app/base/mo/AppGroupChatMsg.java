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
 * (app_group_chat_msg)实体类
 *
 * @author kancy
 * @since 2025-04-08 10:02:02
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_group_chat_msg")
public class AppGroupChatMsg extends Model<AppGroupChatMsg> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 频道
     */
    private String oasisChannelId;
    /**
     * 作者
     */
    private String authorId;
    /**
     * 引用消息
     */
    private String quoteMsgId;
    /**
     * 消息类型
     */
    private String msgType;
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