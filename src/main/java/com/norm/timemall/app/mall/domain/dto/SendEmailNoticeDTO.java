package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendEmailNoticeDTO {
    @NotBlank(message = "noticeType  required")
    private String noticeType;

    private String ref;

}
