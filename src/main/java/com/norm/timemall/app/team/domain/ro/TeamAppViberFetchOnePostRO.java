package com.norm.timemall.app.team.domain.ro;

import com.norm.timemall.app.team.domain.pojo.TeamAppViberPostAuthor;
import com.norm.timemall.app.team.domain.pojo.TeamAppViberPostEmbed;
import lombok.Data;

@Data
public class TeamAppViberFetchOnePostRO {
    private String postId;
    private String textMsg;
    private TeamAppViberPostAuthor author;
    private TeamAppViberPostEmbed embed;
    private String likes;
    private String shares;
    private String comments;
    private String createdAt;
    private String oasisId;
    private String channel;
}
