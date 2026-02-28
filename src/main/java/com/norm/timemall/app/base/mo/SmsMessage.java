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
 * (sms_message)实体类
 *
 * @author kancy
 * @since 2025-03-20 16:34:09
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sms_message")
public class SmsMessage extends Model<SmsMessage> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;

    /**
     * 模版
     */
    private String topic;
    /**
     * 电话号码
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String phone;
    /**
     * ip
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String ip;
    /**
     * 消息
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String body;

    private String sendResponse;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}