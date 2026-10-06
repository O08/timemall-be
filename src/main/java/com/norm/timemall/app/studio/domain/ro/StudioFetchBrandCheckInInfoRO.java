package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchBrandCheckInInfoRO {
    // checkedIn 0-未签到 1-已签到
    private String checkedIn;
}
