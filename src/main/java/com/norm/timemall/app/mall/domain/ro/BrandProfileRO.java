package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class BrandProfileRO {
    private String brand;
    private String avator;
    private String experience;
    // Brand Title or other Info. maybe your Occuption Title
    private String title;
    private String location;
    private String skills;
}
