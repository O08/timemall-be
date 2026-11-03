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
 * (market_puzzle_dream)实体类
 *
 * @author kancy
 * @since 2023-10-20 09:20:42
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("market_puzzle_dream")
public class MarketPuzzleDream extends Model<MarketPuzzleDream> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * eventId
     */
    private String eventId;
    /**
     * content
     */
    private String content;
    /**
     * od
     */
    private Long od;
    /**
     * likes
     */
    private Long likes;
    /**
     * authorBrandId
     */
    private String authorBrandId;
    /**
     * createAt
     */
    private Date createAt;

}