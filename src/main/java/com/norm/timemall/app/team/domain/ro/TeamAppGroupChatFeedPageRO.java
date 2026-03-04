package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamAppGroupChatFeedPageRO {
    private String author;
    private String authorIcon;
    private String authorUserId;
    private String createAt;

    // message Id
    private String id;
    private String msg;
    private String msgType;
    private String quoteAuthor;
    private String quoteMsg;
    private String quoteMsgId;
    private String quoteMsgType;

}
