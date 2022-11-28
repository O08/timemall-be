package com.norm.timemall.app.base.entity;

import cn.hutool.system.UserInfo;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserInfoVO extends CodeVO
{
    private CurrentUser user;
}
