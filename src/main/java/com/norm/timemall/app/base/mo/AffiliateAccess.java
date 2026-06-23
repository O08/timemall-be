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
 * 佣金访问记录(affiliate_access)实体类
 *
 * @author kancy
 * @since 2024-06-11 09:18:59
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("affiliate_access")
public class AffiliateAccess extends Model<AffiliateAccess> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 访问ip
     */
    private String ip;
    /**
     * IP属地
     */
    private String ipLocation;
    /**
     * 带货人brand id
     */
    private String influencer;
    /**
     * 渠道id
     */
    private String outreachChannelId;
    /**
     * 产品
     */
    private String cellId;
    /**
     * 分销方式： api or 通用
     */
    private String market;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}