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
 * (brand_alipay)实体类
 *
 * @author kancy
 * @since 2023-03-16 10:53:39
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("brand_alipay")
public class BrandAlipay extends Model<BrandAlipay> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brandId
     */
    private String brandId;
    /**
     * payType
     */
    private String payType;
    /**
     * payeeAccount
     */
    private String payeeAccount;
    /**
     * payeeRealName
     */
    private String payeeRealName;
    /**
     * createAt
     */
    private Date createAt;
    /**
     * modifiedAt
     */
    private Date modifiedAt;

}