package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (sequence)实体类
 *
 * @author kancy
 * @since 2023-10-20 11:31:02
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sequence")
public class Sequence extends Model<Sequence> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * key
     */
    @TableId
	private String key;
    /**
     * 序列号
     */
    private Integer currentNo;
    /**
     * 自增步长
     */
    private Integer step;

}