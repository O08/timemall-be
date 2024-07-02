package com.norm.timemall.app.ms.domain.ro;

import lombok.Data;

@Data
public class MsFetchPrivateFriendRO {
    private String avatar;
    private String title;
    private String id;
    private String unread;
    private String latestContent;
    private String modifiedAt;
    private String brandMark;
}
