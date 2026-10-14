package com.norm.timemall.app.pod.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class PodChangeProgramThumbnailDTO {
    private MultipartFile thumbnail;

    @NotBlank(message = "programId required")
    private String programId;
}
