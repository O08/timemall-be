package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MallGetRcListRO {
    private String downloadUri;
    private String previewUri;
    private String thumbnail;
    private String title;
    private ArrayList<String> tags;
}
