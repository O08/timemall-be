package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamAppFbNewAttachmentDTO {
    @NotBlank(message = "feedId required")
    private String feedId;

    private MultipartFile attachment;
}
