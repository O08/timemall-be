package com.norm.timemall.app.ms.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.ms.domain.pojo.MsFetchPrivateFriend;
import lombok.Data;

@Data
public class MsFetchPrivateFriendVO extends CodeVO {
    private MsFetchPrivateFriend friend;
}
