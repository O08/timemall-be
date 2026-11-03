package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

/**
 * (user_need_story)实体类
 *
 * @author kancy
 * @since 2023-11-17 10:05:43
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user_need_story")
public class UserNeedStory extends Model<UserNeedStory> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 用户需求
     */
    private String descriptions;
    /**
     * 预算
     */
    private Integer budget;
    /**
     * 联系方式
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String contactInfo;
    /**
     * 反馈处理状态
     */
    private String tag;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}