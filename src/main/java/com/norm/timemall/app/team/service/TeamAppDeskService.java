package com.norm.timemall.app.team.service;

import com.norm.timemall.app.base.mo.AppDeskElement;
import com.norm.timemall.app.base.mo.AppDeskTopic;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.TeamAppDeskGetElementsVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppDeskService {
    TeamAppDeskGetElementsVO findElements(TeamAppDeskGetElementsDTO dto);

    void addOneTopic(TeamAppDeskNewTopicDTO dto);

    void delOneTopic(String topicId);

    void delElementsOwnedByTopic(String topicId);

    AppDeskTopic findOneTopic(String topicId);

    void editTopic(TeamAppDeskEditTopicDTO dto);

    void reorderTopic(TeamAppDeskReorderTopicDTO dto);

    void addOneElement(AppDeskNewElementDTO dto, String iconUrl);

    AppDeskElement findOneElement(String id);

    void delOneElement(String id);

    void changeElement(AppDeskEditElementDTO dto);

    void storeElementStatisticsData(String id);

    void reorderTopicWhenDel(String oasisChannelId, Long od);

    void removeChannelData(String channel);
}
