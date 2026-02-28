package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

@Data
public class TeamAppViberPostEmbedVideo {
    private String url;
    private String title;
    private String description;
    private String contentType;
    private TeamAppViberPostEmbedVideoProvider provider;
    private TeamAppViberPostEmbedVideoThumbnail thumbnail;

}
