package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamAppCardNewFeedDTO {

    private MultipartFile coverFile;

    @NotBlank(message = "subtitle required")
    @Length(message = "subtitle length must in range {min}-{max}",min = 0,max = 38)
    private String subtitle;


    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 38)
    private String title;

    @NotBlank(message = "linkUrl required")
    @Length(message = "linkUrl length must in range {min}-{max}",min = 1,max = 500)
    private String linkUrl;


    @NotBlank(message = "channel required")
    private String channel;
}
