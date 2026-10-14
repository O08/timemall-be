package com.norm.timemall.app.base.mo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (coop_programs)实体类
 *
 * @author kancy
 * @since 2026-07-03 09:22:21
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("coop_programs")
public class CoopPrograms extends Model<CoopPrograms> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * authorBrandId
     */
    private String authorBrandId;
    /**
     * title
     */
    private String title;
    /**
     * thumbnail
     */
    private String thumbnail;
    /**
     * description
     */
    private String description;
    /**
     * onlineLink
     */
    private String onlineLink;
    /**
     * workMode
     */
    private String workMode;

    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object topics;

    /**
     * buzz
     */
    private Integer buzz;
    /**
     * applys
     */
    private Integer applys;
    /**
     * attendees
     */
    private Integer attendees;
    /**
     * status
     */
    private String status;
    /**
     * createdAt
     */
    private Date createdAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}