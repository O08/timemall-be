package com.norm.timemall.app.team.domain.ro;

import com.norm.timemall.app.team.domain.pojo.TeamAppDeskGetElement;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppDeskGetElementsRO {
    private String topicId;
    private String topicPreface;
    private String topicTitle;
    private ArrayList<TeamAppDeskGetElement> elements;

}
