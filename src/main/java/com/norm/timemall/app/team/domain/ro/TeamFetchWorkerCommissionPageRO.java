package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamFetchWorkerCommissionPageRO {
    private String id;
    private String title;
    private String bonus;

    private String oasisAvatar;
    private String oasisHandle;
    private String oasisName;

    private String tag;
    private String createAt;
    private String signAt;
    private String deliveryCycle;
}
