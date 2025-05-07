package com.norm.timemall.app.mall.domain.pojo;


import com.norm.timemall.app.mall.domain.ro.MallHomeVirtualProductRO;
import lombok.Data;

import java.util.ArrayList;


@Data
public class MallHomeInfo {
    private String brand;
    private String browseBrandId;
    private String brandTitle;
    private String pdOasisId;
    private String avator;
    private String cover;
    // 激活蓝标： 0 未激活 1 激活
    private String enableBlue;
    private ArrayList<MallHomeVirtualProductRO> vr;
}
