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
 * (event_feed)实体类
 *
 * @author kancy
 * @since 2023-05-13 13:38:34
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("event_feed")
public class EventFeed extends Model<EventFeed> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 事件流类型
     */
    private String scene;
    /**
     * 事件流上游
     */
    private String up;
    /**
     * 事件流下游
     */
    private String down;
    /**
     * 消息
     */
    private String feed;
    /**
     * 状态标记
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