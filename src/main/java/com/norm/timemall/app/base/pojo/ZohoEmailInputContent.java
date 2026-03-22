package com.norm.timemall.app.base.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ZohoEmailInputContent {
    private ZohoFrom from;
    private ArrayList<ZohoToEmail> to;
    private String subject;
    private String htmlbody;
}
