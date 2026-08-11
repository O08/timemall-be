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
 * (ppc_visit)实体类
 *
 * @author kancy
 * @since 2024-10-03 15:48:46
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("ppc_visit")
public class PpcVisit extends Model<PpcVisit> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * 监测码
     */
    private String trackCode;
    /**
     * 链接
     */
    private String linkAddress;
    /**
     * 来源
     */
    private String sourceAddress;
    /**
     * ip地址
     */
    private String ip;
    /**
     * 设备信息
     */
    private String deviceInfo;
    /**
     * 采购价格
     */
    private BigDecimal price;
    /**
     * 合法标识：1 合法流量，0 灰产流量
     */
    private String valid;
    /**
     * 结算标识： 1 已结算 ，0 待结算
     */
    private String pay;
    /**
     * 结算批次
     */
    private String batch;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 修改时间
     */
    private Date modifiedAt;

}