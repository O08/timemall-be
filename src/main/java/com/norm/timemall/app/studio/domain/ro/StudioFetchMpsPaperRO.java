package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchMpsPaperRO {
    private String id;
    private String title;
    private String supplier;
    private String purchaser;
    private String purchaserAvatar;
    private String purchaserUserId;
    private String piece;
    private String bonus;
    private String createAt;
    private String tag;

}
