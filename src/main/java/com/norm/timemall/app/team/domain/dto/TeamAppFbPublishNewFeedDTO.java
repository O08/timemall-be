package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamAppFbPublishNewFeedDTO {
    private MultipartFile coverFile;


    @Length(message = "preface length must in range {min}-{max}",min = 0,max = 38)
    private String preface;

    @NotBlank(message = "richMediaContent required")
    private String richMediaContent;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 38)
    private String title;

    @Length(message = "ctaPrimaryLabel length must in range {min}-{max}",min = 0,max = 6)
    private String ctaPrimaryLabel;

    @Length(message = "ctaPrimaryUrl length must in range {min}-{max}",min = 0,max = 500)
    private String ctaPrimaryUrl;

    @Length(message = "ctaSecondaryLabel length must in range {min}-{max}",min = 0,max = 6)
    private String ctaSecondaryLabel;

    @Length(message = "ctaSecondaryUrl length must in range {min}-{max}",min = 0,max = 500)
    private String ctaSecondaryUrl;

    @NotBlank(message = "channel required")
    private String channel;

}
