package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamDspAddCaseDTO {

    private MultipartFile material;

    @NotBlank(message = "fraudType required")
    @Length(message = "fraudType length must in range {min}-{max}",min = 0,max = 38)
    private String fraudType;

    @NotBlank(message = "scene required")
    @Length(message = "scene length must in range {min}-{max}",min = 0,max = 38)
    private String scene;

    @NotBlank(message = "sceneUrl required")
    @Length(message = "sceneUrl length must in range {min}-{max}",min = 0,max = 480)
    private String sceneUrl;

    @NotBlank(message = "caseDesc required")
    @Length(message = "caseDesc length must in range {min}-{max}",min = 0,max = 320)
    private String caseDesc;


}
