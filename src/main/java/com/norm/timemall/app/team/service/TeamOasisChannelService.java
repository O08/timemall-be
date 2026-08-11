package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.PutChannelGeneralDTO;
import com.norm.timemall.app.team.domain.dto.RefreshOasisChannelSortDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisChannelListRO;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface TeamOasisChannelService {
    List<FetchOasisChannelListRO> findOasisChannelList(String oasisId);

    void removeOasisChannel(String oasisChannelId);

    void modifyChannelGeneralInfo(PutChannelGeneralDTO dto);

    FetchOneOasisChannelGeneralInfoRO findOasisOneChannelGeneralInfo(String och);

    void modifyChannelSortInfo(RefreshOasisChannelSortDTO dto);

    ArrayList<String> findChannelSort(String oasisId);

    ArrayList<FetchOasisChannelListRO> findPublicChannelList(String oasisId);
}
