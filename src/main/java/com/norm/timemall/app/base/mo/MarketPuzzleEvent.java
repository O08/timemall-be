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
 * (market_puzzle_event)实体类
 *
 * @author kancy
 * @since 2023-10-20 09:20:42
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_puzzle_event")
public class MarketPuzzleEvent extends Model<MarketPuzzleEvent> implements Serializable {
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
     * eventRule
     */
    private String eventRule;
    /**
     * eventRef
     */
    private String eventRef;
    /**
     * winner
     */
    private String winner;
    /**
     * sponsor
     */
    private String sponsor;
    /**
     * sponsor
     */
    private BigDecimal bonus;
    /**
     * od
     */
    private Integer od;
    /**
     * tag
     */
    private String tag;
    /**
     * keyWhere
     */
    private String keyWhere;
    /**
     * keyPrint
     */
    private String keyPrint;
    /**
     * beginAt
     */
    private Date beginAt;
    /**
     * endAt
     */
    private Date endAt;

}