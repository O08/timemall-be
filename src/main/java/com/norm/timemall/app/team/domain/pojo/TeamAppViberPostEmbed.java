package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppViberPostEmbed {
    private String facet;
    private ArrayList<TeamAppViberPostEmbedAttachment> attachments;
    private ArrayList<TeamAppViberPostEmbedImage> images;
    private ArrayList<TeamAppViberPostEmbedLink> links;
    private ArrayList<TeamAppViberPostEmbedVideo> videos;
}
