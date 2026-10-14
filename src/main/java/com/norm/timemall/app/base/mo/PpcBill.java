package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (ppc_bill)实体类
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ppc_bill")
public class PpcBill extends Model<PpcBill> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * brand id
     */
    private String supplierBrandId;
    /**
     * 结算批次
     */
    private String batch;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 账单状态：1-财务审核，2-已打款
     */
    private String tag;
    /**
     * 结算IP数
     */
    private Long ips;
    /**
     * 账务责任人用户Id
     */
    private String adminCustomerId;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}