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
 * (email_message)实体类
 *
 * @author kancy
 * @since 2022-11-14 11:15:15
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("email_message")
public class EmailMessage extends Model<EmailMessage> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId
	private Long id;
    /**
     * 发件人
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String sender;
    /**
     * 收件人
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String recipient;
    /**
     * 主题
     */
    private String topic;
    /**
     * 发送内容
     */
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String body;
    /**
     *  参考因子
     */
    private String ref;
    /**
     * createAt
     */
    private Date createAt;

}