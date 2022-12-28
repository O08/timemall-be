package com.norm.timemall.app.base.pojo.ro;

import com.norm.timemall.app.mall.domain.pojo.Fee;
import com.norm.timemall.app.mall.domain.pojo.MallCellIntroContent;
import lombok.Data;

import java.util.ArrayList;

@Data
public class CellIntroRO {
    // The title for cell
    private String title ;

    // The id for cell
    private String id;

    // The intro cover url for cell
    private String cover;

    // The intro cover url for cell
    private String introCover;
    //The brand Id of providing cell
    private String brandId;

    //The brand of providing cell
    private String brand;

    // The avator url for brand
    private String avator;

    // The Intro for cell
    private MallCellIntroContent content;

    // The pricing for cell
    private ArrayList<Fee> fee;


}
