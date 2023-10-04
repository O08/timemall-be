package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (feed_back)实体类
 *
 * @author kancy
 * @since 2023-10-02 11:22:35
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("feed_back")
public class FeedBack extends Model<FeedBack> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 反馈的问题
     */
    private String issue;
    /**
     * 联系方式
     */
    @FieldEncrypt
    private String contactInfo;
    /**
     * 反馈附件
     */
    private String attachment;
    /**
     * 状态
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