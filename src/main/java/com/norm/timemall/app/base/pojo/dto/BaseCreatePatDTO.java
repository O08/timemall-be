package com.norm.timemall.app.base.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
public class BaseCreatePatDTO {
    @NotBlank(message = "名称不能为空")
    @Length(min = 1, max = 50, message = "名称长度不能超过50个字符")
    private String name;

    private Integer daysToLive;   // Null means never expires

}
