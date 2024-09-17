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
 * (oasis)实体类
 *
 * @author kancy
 * @since 2023-02-27 10:31:05
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("oasis")
public class Oasis extends Model<Oasis> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 发起人brand id
     */
    private String initiatorId;
    /**
     * 标题
     */
    private String title;
    /**
     * 子标题
     */
    private String subtitle;
    /**
     * 个性封面
     */
    private String avatar;
    /**
     * 成员数
     */
    private Integer membership;
    /**
     * 最大会员容量
     */
    private Integer maxMembers;
    /**
     * 风险
     */
    private String risk;
    /**
     * 宣言
     */
    private String announce;

    private String mark;

    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;
    /**
     * 频道顺序
     */
    private Object channelSort;

}