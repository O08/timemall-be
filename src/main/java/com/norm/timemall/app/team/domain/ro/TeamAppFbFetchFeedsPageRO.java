package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamAppFbFetchFeedsPageRO {
    private String author;
    private String authorBrandId;
    private String coverUrl;
    private String createAt;
    private String ctaPrimaryLabel;
    private String ctaPrimaryUrl;
    private String ctaSecondaryLabel;
    private String ctaSecondaryUrl;
    private String highlight;
    private String preface;
    private String richMediaContent;
    private String title;
    private String id; // feed id
}
