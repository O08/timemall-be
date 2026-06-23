package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamOfficeQueryDepartmentPageDTO extends PageDTO {
    private String q;
    private String oasisId;
    private String encryptedQ;
}
