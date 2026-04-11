package com.norm.timemall.app.ms.domain.pojo;

import com.norm.timemall.app.ms.domain.ro.MsFetchPrivateFriendRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class MsFetchPrivateFriend {
    private ArrayList<MsFetchPrivateFriendRO> records;

}
