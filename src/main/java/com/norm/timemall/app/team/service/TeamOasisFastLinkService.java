package com.norm.timemall.app.team.service;

import com.norm.timemall.app.base.mo.OasisFastLink;
import com.norm.timemall.app.team.domain.dto.TeamAddNewFastLinkDTO;
import com.norm.timemall.app.team.domain.ro.FetchFastLinkRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamOasisFastLinkService {
    ArrayList<FetchFastLinkRO> findFastLinks(String id);

    boolean validateLinkMaxLimit(String oasisId);

    void addOneFastLink(TeamAddNewFastLinkDTO dto, String logoUrl);



    void removeOneFastLink(String id);

    OasisFastLink findOneFastLink(String id);
}
