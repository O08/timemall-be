package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamAppRedeemChangeProductThumbnailDTO {
    private MultipartFile thumbnail;
    @NotBlank(message = "productId required")
    private String productId;
}
