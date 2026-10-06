package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamAppViberFetchCommentPageRO {
    private String cid;
    private String textMsg;

    private String authorBrandId;
    private String authorName;
    private String authorUserId;
    private String authorAvatar;

    private String dislike;
    private String likes;

    private String interactLikeAction;
    private String createAt;


}
