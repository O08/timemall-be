package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

@Data
public class FeedbackDTO {

    @NotBlank(message = "issue is required")
    @Length(message = "issue length must in range {min}-{max}",min = 1,max = 300)
    private String issue;
    private String contactInfo;

    private MultipartFile attachment;

}
