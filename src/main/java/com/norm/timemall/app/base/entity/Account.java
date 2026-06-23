package com.norm.timemall.app.base.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Account {
    private String userId;
    private String name;
    private String password;
}
