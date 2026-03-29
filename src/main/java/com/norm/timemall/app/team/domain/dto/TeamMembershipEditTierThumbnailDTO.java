package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamMembershipEditTierThumbnailDTO {
    private MultipartFile thumbnail;

    @NotBlank(message = "tierId required")
    private String tierId;
}
