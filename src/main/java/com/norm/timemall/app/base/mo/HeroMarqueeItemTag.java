package com.norm.timemall.app.base.mo;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * (hero_marquee_item_tag)实体类
 *
 * @author kancy
 * @since 2024-03-30 10:28:07
 * @description 由 Mybatisplus Code Generator 创建
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("hero_marquee_item_tag")
public class HeroMarqueeItemTag extends Model<HeroMarqueeItemTag> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
	private String id;
    /**
     * hero_marquee_item.id
     */
    private String itemId;
    /**
     * hero_marquee_tag.id
     */
    private String tagId;

}