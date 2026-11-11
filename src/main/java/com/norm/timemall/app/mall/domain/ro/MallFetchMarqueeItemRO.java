package com.norm.timemall.app.mall.domain.ro;

import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeItem;
import com.norm.timemall.app.mall.domain.pojo.MallFetchMarqueeItemTag;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MallFetchMarqueeItemRO {
    private String displayName;
    private String displayTitle;
    private String photo;
    private ArrayList<MallFetchMarqueeItemTag> tags;

}
