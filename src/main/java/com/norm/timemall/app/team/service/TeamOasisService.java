package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.OasisCreatedByCurrentBrand;
import com.norm.timemall.app.team.domain.pojo.TeamOasisAnnounce;
import com.norm.timemall.app.team.domain.pojo.TeamOasisIndex;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisService {
    IPage<TeamOasisRO> findOasis(TeamOasisPageDTO pageDTO);

    Oasis findOneById(String oasisId);

    void modifyOasisAnnounce(String oasisId, String uri);

    void modifyOasisRisk(TeamOasisRiskDTO dto);

    String newOasis(TeamNewOasisDTO dto);

    TeamOasisAnnounce findOasisAnnounce(String oasisId);

    TeamOasisIndex findOasisValIndex(String oasisId);

    void modifyOasisAvatar(String oasisId, String uri);

    void tagOasisTag(String oasisId, String mark);

    void modifyOasisBaseInfo(TeamOasisGeneralDTO dto);

    OasisCreatedByCurrentBrand findOasisCreatedByCurrentBrand();


    void doSetting(TeamOasisSettingDTO dto);

    void blockedOasis(String oasisId);

    void changeOasisManager(TeamOasisChangeManagerDTO dto);
}
