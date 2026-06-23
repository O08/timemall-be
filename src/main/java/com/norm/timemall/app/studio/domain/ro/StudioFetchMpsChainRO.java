package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchMpsChainRO {
    private String id;
    private String title;
    private String processingCnt;
    private String processedCnt;
    private String createAt;
    private String tag;
}
