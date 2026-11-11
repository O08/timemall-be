package com.norm.timemall.app.base.mo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (app_meetr_event)实体类
 *
 * @author kancy
 * @since 2026-06-17 19:31:48
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("app_meetr_event")
public class AppMeetrEvent extends Model<AppMeetrEvent> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * title
     */
    private String title;
    /**
     * category
     */
    private String category;
    /**
     * thumbnail
     */
    private String thumbnail;
    /**
     * hostedBrandId
     */
    private String hostedBrandId;
    /**
     * oasisId
     */
    private String oasisId;
    /**
     * oasisChannelId
     */
    private String oasisChannelId;
    /**
     * description
     */
    private String description;
    /**
     * location
     */
    private String location;
    /**
     * activityStartAt
     */
    private LocalDateTime activityStartAt;
    /**
     * topics
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Object topics;
    /**
     * attendees
     */
    private Integer attendees;
    /**
     * duration
     */
    private Integer duration;
    /**
     * durationType
     */
    private String durationType;
    /**
     * maxSeats
     */
    private Integer maxSeats;
    /**
     * allowGuests
     */
    private String allowGuests;
    /**
     * budget
     */
    private BigDecimal budget;
    /**
     * eventType
     */
    private String eventType;
    /**
     * onlineLink
     */
    private String onlineLink;
    /**
     * eventStatus
     */
    private String eventStatus;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}