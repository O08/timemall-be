package com.norm.timemall.app.base.mo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@TableName("sys_richtext_config")
// 系统富文本表， 配置富文本如邮件html
@Accessors(chain = true)
public class RichTextConfig {
    @TableId(type = IdType.AUTO)
    private Long id ;
    /**
     * 富文本类型
     */
    private String contentType;

    /**
     * 富文本编号
     */
    private String contentNo;

    /**
     * 富文本简介
     */
    private String contentDesc;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private Date createdate;

    /**
     * 修改时间
     */
    private Date modifydate;
}
