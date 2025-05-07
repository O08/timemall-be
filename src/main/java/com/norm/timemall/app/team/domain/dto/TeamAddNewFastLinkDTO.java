package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamAddNewFastLinkDTO {

    private MultipartFile logo;

    @Length(message = "title length must in range {min}-{max}",min = 1,max = 10)
    @NotBlank(message = "title required")
    private String title;

    @Length(message = "linkUrl length must in range {min}-{max}",min = 1,max = 500)
    @NotBlank(message = "linkUrl required")
    private String linkUrl;

    @Length(message = "linkDetail length must in range {min}-{max}",min = 1,max = 260)
    @NotBlank(message = "linkDetail required")
    private String linkDetail;

    @NotBlank(message = "oasisId required")
    private String oasisId;

}
