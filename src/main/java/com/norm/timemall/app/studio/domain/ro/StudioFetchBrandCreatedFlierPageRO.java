package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

import java.util.Date;

@Data
public class StudioFetchBrandCreatedFlierPageRO {
    private String flierId;
    private String title;
    private String description;
    private String ctaLink;
    private String contentLink;
    private Integer likes;
    private Integer copies;
    private Integer ctaClicks;
    private String status;
    private Date createdAt;
    private Date modifiedAt;
}
