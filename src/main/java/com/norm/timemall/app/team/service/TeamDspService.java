package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseLibraryPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseListPageRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchDspCaseLibraryPageVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchDspCaseListPageVO;
import com.norm.timemall.app.team.domain.vo.TeamGetDspCaseMaterialVO;
import com.norm.timemall.app.team.domain.vo.TeamGetDspOneCaseInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamDspService {
    void newCase(TeamDspAddCaseDTO dto,String materialUrl);

    IPage<TeamFetchDspCaseListPageRO> findDspCaseList(TeamFetchDspCaseListPageDTO dto);

    IPage<TeamFetchDspCaseLibraryPageRO>  findDspCaseLibrary(TeamFetchDspCaseLibraryPageDTO dto);

    TeamGetDspOneCaseInfoVO findDspOneCaseInfo(String caseNO);

    TeamGetDspCaseMaterialVO findDspCaseMaterial(String caseNO, String materialType);

    void newCaseMaterial(TeamDspAddCaseMaterialDTO dto, String materialName, String materialUrl);

    void modifyCase(TeamDspCaseChangeDTO dto);

    void doOfflineCell(String id);

    void withdrawToAlipayCreditSetting(WithdrawToAlipayCreditSettingDTO dto);

}
