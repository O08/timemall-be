package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsFetchPrivateFriendProfile;
import lombok.Data;

@Data
public class MsFetchPrivateFriendProfileVO extends CodeVO {
    private MsFetchPrivateFriendProfile profile;
}
