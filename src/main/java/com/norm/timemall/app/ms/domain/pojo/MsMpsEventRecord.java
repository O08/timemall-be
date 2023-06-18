package com.norm.timemall.app.ms.domain.pojo;

import lombok.Data;

import java.util.ArrayList;


@Data
public class MsMpsEventRecord {
    private String stampDate;
    private ArrayList<MsMpsEventCard> cards;
}
