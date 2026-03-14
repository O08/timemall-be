package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamOfficeChangeEmployeePhotoDTO {

    @NotBlank(message = "employeeId required")
    private String employeeId;

    private MultipartFile photoFile;
}
