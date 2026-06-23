package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AppDeskNewElementDTO {
    private MultipartFile iconFile;

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 35)
    private String title;

    @NotBlank(message = "preface required")
    @Length(message = "preface length must in range {min}-{max}",min = 0,max = 68)
    private String preface;

    @NotBlank(message = "linkUrl required")
    @Length(message = "linkUrl length must in range {min}-{max}",min = 0,max = 480)
    private String linkUrl;

    @NotBlank(message = "topicId required")
    private String topicId;

}
