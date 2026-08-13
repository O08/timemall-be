package com.norm.timemall.app.base.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseCreatePatBO {
    private String name;
    private String rawToken;      // The actual secret
    private LocalDateTime expiresAt;
    public BaseCreatePatBO(String name, String rawToken, LocalDateTime expiresAt) {
        this.name = name;
        this.rawToken = rawToken;
        this.expiresAt = expiresAt;
    }
}
