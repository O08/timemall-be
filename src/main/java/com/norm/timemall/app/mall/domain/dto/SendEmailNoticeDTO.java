package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class SendEmailNoticeDTO {
    @NotBlank(message = "noticeType  required")
    private String noticeType;

    private String ref;

}
