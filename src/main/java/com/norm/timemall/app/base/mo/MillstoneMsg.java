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
 * (millstone_msg)实体类
 *
 * @author kancy
 * @since 2023-05-06 09:44:52
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("millstone_msg")
public class MillstoneMsg extends Model<MillstoneMsg> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * msgId
     */
    @TableId
	private String msgId;
    /**
     * 发贴brand id
     */
    private String authorId;
    /**
     * 与订单id相同
     */
    private String millstoneId;
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