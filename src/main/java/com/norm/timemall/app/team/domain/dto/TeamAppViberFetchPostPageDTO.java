package com.norm.timemall.app.team.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TeamAppViberFetchPostPageDTO extends PageDTO {
    private String q;
    private String channel;
    private String sort;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endAt;
}
