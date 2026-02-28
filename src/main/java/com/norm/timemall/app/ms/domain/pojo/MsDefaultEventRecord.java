package com.norm.timemall.app.ms.domain.pojo;

import lombok.Data;

import java.util.ArrayList;


@Data
public class MsDefaultEventRecord {
    private String stampDate;
    private ArrayList<MsDefaultEventCard> cards;
}
