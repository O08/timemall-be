package com.norm.timemall.app.ms.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MsMillstoneEventRecord {
    private String stampDate;
    private ArrayList<MsMillstoneEventCard> cards;
}
