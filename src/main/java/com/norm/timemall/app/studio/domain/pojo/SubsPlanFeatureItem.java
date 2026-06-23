package com.norm.timemall.app.studio.domain.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubsPlanFeatureItem {
    private String title;
    private String description;
}
