package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppViberPostEmbedComic {
    private String title; // 漫画名
    private  String genre; // 题材
    private String cover; // 封面
    private String chapter; // 章节
    private ArrayList<TeamAppViberPostEmbedImage> images;
}
