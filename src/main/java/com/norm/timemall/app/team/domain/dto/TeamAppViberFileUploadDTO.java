package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppViberCommentInteractEventEnum;
import com.norm.timemall.app.base.enums.AppViberFileSceneEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data

public class TeamAppViberFileUploadDTO {

    @NotBlank(message = "Channel is required")
    private String channel;

    @NotNull(message = "File is required")
    private MultipartFile file;

    @NotBlank(message = "Scene is required")
    @EnumCheck(enumClass = AppViberFileSceneEnum.class, message = "scene must be 'image' or 'attachment'")
    private String scene;

}