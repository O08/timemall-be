package com.norm.timemall.app.indicator.domain.ro;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class IndCellIndicesRO {
    // cell id
    private String id;
    // cell title
    private String title;
    // cell mark
    private String mark;
    // 曝光率
    private Integer impressions;
    // 点击数
    private Integer clicks;
    // 订单数
    private Integer orders;
    // 单品小鸟销量
    private Integer birdPurchases;
    // 单品老鹰销量
    private Integer eaglePurchases;
    // 单品信天翁销量
    private Integer albatrossPurchases;

}
